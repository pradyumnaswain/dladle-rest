package za.co.dladle.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.*;
import za.co.dladle.exception.PropertyAddException;
import za.co.dladle.exception.PropertyAlreadyExistsException;
import za.co.dladle.mapper.ContactTypeMapper;
import za.co.dladle.mapper.PlaceTypeMapper;
import za.co.dladle.model.*;
import za.co.dladle.session.UserSession;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by jugal on 05/02/2017.
 */

@Service
public class PropertyService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    private UserServiceUtility userServiceUtility;

    @Autowired
    private AndroidPushNotificationsService pushNotificationsService;

    @Autowired
    private PushNotificationService notificationService;

    @Autowired
    private NotificationServiceSendGridImpl emailService;

    private static final Logger log = LoggerFactory.getLogger(PropertyService.class);

    //------------------------------------------------------------------------------------------------------------------
    //Insert Property
    //------------------------------------------------------------------------------------------------------------------

    @Transactional
    public boolean addProperty(PropertyAddRequest property) throws PropertyAlreadyExistsException, PropertyAddException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {
            String email = userSession.getUser().getEmailId();

            Map<String, Object> map = new HashMap<>();
            map.put("address", property.getAddress());
            map.put("emailId", email);
            String landlordSql = "SELECT landlord.id landord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=:emailId";
            Integer landlordId = this.parameterJdbcTemplate.queryForObject(landlordSql, map, Integer.class);

            map.put("landlordId", landlordId);

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("landlordId", landlordId)
                    .addValue("address", property.getAddress())
                    .addValue("PlaceType", PlaceTypeMapper.getPlaceType(property.getPlaceType()))
                    .addValue("complexName", property.getComplexName())
                    .addValue("unitNumber", property.getUnitNo())
                    // TODO: 6/3/2017 Adding Image
                    .addValue("imgUrl", "image_url")
                    .addValue("isEstate", property.isInEstate())
                    .addValue("estateName", property.getEstateName());

            String countSql = "SELECT COUNT(address) FROM property WHERE address=:address AND landlord_id=:landlordId";

            Integer countAddress = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

            if (countAddress == 0) {

                KeyHolder propertyId = new GeneratedKeyHolder();

                String PropertySql = "INSERT INTO property (landlord_id, address, place_type_id, complex_name, unit_number, image_url, isEstate, estate_name) VALUES (:landlordId, :address, :PlaceType,:complexName,:unitNumber,:imgUrl,:isEstate, :estateName)";

                this.parameterJdbcTemplate.update(PropertySql, mapSqlParameterSource, propertyId, new String[]{"id"});

                mapSqlParameterSource.addValue("propertyId", propertyId.getKey().longValue());

                KeyHolder houseId = new GeneratedKeyHolder();
                String sqlHouse = "INSERT INTO house(property_id) VALUES (:propertyId)";
                this.parameterJdbcTemplate.update(sqlHouse, mapSqlParameterSource, houseId, new String[]{"id"});

                List<Map<String, Object>> list = new ArrayList<>();

                for (PropertyContact propertyContact : property.getPropertyContactList()) {
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("houseId", houseId.getKey().longValue());
                    map1.put("contactType", ContactTypeMapper.getPropertyContactType(propertyContact.getContactType()));
                    map1.put("name", propertyContact.getName());
                    map1.put("address", propertyContact.getAddress());
                    map1.put("contactNumber", propertyContact.getContactNumber());
                    list.add(map1);
                }
                String sql = "INSERT INTO property_contact(house_id, contact_type_id, name, address, contact_number) VALUES (:houseId,:contactType,:name,:address,:contactNumber)";
                this.parameterJdbcTemplate.batchUpdate(sql, list.toArray(new Map[property.getPropertyContactList().size()]));

                return true;
            } else {
                throw new PropertyAlreadyExistsException("Property already Exists");
            }
        } else {
            throw new PropertyAddException("Property can only be added by Landlord");
        }
    }

    public List<Property> listProperties() throws PropertyAddException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {
            String email = userSession.getUser().getEmailId();

            List<Property> propertyList = new ArrayList<>();

            Map<String, Object> map = new HashMap<>();
            map.put("emailId", email);
            String landlordSql = "SELECT property.*,property.id property_id, house.id house_id,place_type.name place_name FROM property " +
                    " INNER JOIN landlord ON property.landlord_id= landlord.id" +
                    " INNER JOIN place_type ON property.place_type_id= place_type.id" +
                    " INNER JOIN house ON property.id= house.property_id " +
                    " INNER JOIN user_dladle ON landlord.user_id = user_dladle.id " +
                    " WHERE emailid=:emailId";
            this.parameterJdbcTemplate.query(landlordSql, map, (rs, rowNum) -> {
                Property property = new Property();
                property.setAddress(rs.getString("address"));
                property.setComplexName(rs.getString("complex_name"));
                property.setEstate(rs.getBoolean("isestate"));
                property.setEstateName(rs.getString("estate_name"));
                property.setPlaceImage(rs.getString("image_url"));
                property.setUnitNumber(rs.getString("unit_number"));
                property.setPlaceType(rs.getString("place_name"));
                property.setPropertyId(rs.getLong("property_id"));
                property.setHouseId(rs.getLong("house_id"));

                List<PropertyContactView> contacts = new ArrayList<>();
                map.put("houseId", property.getHouseId());
                String sql = "SELECT property_contact.*,property_contact.name property_conact_name, contact_type.name contact_type_name FROM property_contact INNER JOIN contact_type ON property_contact.contact_type_id = contact_type.id WHERE house_id=:houseId";
                this.parameterJdbcTemplate.query(sql, map, (rs1, rowNum1) -> {
                    PropertyContactView propertyContact = new PropertyContactView();
                    propertyContact.setAddress(rs1.getString("address"));
                    propertyContact.setName(rs1.getString("property_conact_name"));
                    propertyContact.setContactType(rs1.getString("contact_type_name"));
                    propertyContact.setContactNumber(rs1.getString("contact_number"));
                    contacts.add(propertyContact);
                    return propertyContact;
                });
                property.setPropertyContactList(contacts);

                List<String> tenantList = new ArrayList<>();
                map.put("houseId", property.getHouseId());
                String sql1 = "SELECT user_dladle.* FROM tenant INNER JOIN user_dladle ON tenant.user_id = user_dladle.id WHERE house_id=:houseId";
                this.parameterJdbcTemplate.query(sql1, map, (rs1, rowNum1) -> {
                    String tenant = rs1.getString("emailid");
                    tenantList.add(tenant);
                    return tenant;
                });
                property.setTenantList(tenantList);

                propertyList.add(property);

                return property;
            });
            return propertyList;
        } else if (userSession.getUser().getUserType().eqTENANT()) {
            String email = userSession.getUser().getEmailId();

            List<Property> propertyList = new ArrayList<>();

            Map<String, Object> map = new HashMap<>();
            map.put("emailId", email);
            String landlordSql = "SELECT property.*,property.id property_id, house.id house_id,place_type.name place_name FROM property " +
                    " INNER JOIN place_type ON property.place_type_id= place_type.id" +
                    " INNER JOIN house ON property.id= house.property_id " +
                    " INNER JOIN tenant ON house.id = tenant.house_id " +
                    " INNER JOIN user_dladle ON tenant.user_id = user_dladle.id " +
                    " WHERE emailid=:emailId";
            this.parameterJdbcTemplate.query(landlordSql, map, (rs, rowNum) -> {
                Property property = new Property();
                property.setAddress(rs.getString("address"));
                property.setComplexName(rs.getString("complex_name"));
                property.setEstate(rs.getBoolean("isestate"));
                property.setEstateName(rs.getString("estate_name"));
                property.setPlaceImage(rs.getString("image_url"));
                property.setUnitNumber(rs.getString("unit_number"));
                property.setPlaceType(rs.getString("place_name"));
                property.setPropertyId(rs.getLong("property_id"));
                property.setHouseId(rs.getLong("house_id"));

                List<PropertyContactView> contacts = new ArrayList<>();
                map.put("houseId", property.getHouseId());
                String sql = "SELECT property_contact.*,property_contact.name property_conact_name, contact_type.name contact_type_name FROM property_contact INNER JOIN contact_type ON property_contact.contact_type_id = contact_type.id WHERE house_id=:houseId";
                this.parameterJdbcTemplate.query(sql, map, (rs1, rowNum1) -> {
                    PropertyContactView propertyContact = new PropertyContactView();
                    propertyContact.setAddress(rs1.getString("address"));
                    propertyContact.setName(rs1.getString("property_conact_name"));
                    propertyContact.setContactType(rs1.getString("contact_type_name"));
                    propertyContact.setContactNumber(rs1.getString("contact_number"));
                    contacts.add(propertyContact);
                    return propertyContact;
                });
                property.setPropertyContactList(contacts);

                propertyList.add(property);

                return property;
            });
            return propertyList;
        } else {
            throw new PropertyAddException("Property Unavailable");
        }
    }

    public void assignPropertyToTenant(PropertyAssignmentRequest propertyAssignmentRequest) throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        Map<String, Object> map = new HashMap<>();
        map.put("landlordEmailId", propertyAssignmentRequest.getEmailId());
        String landlordSql = "SELECT landlord.id landord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=:landlordEmailId";
        Integer landlordId = this.parameterJdbcTemplate.queryForObject(landlordSql, map, Integer.class);

        map.put("tenantEmailId", userSession.getUser().getEmailId());
        String tenantSql = "SELECT landlord.id landord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=:tenantEmailId";
        Integer tenantId = this.parameterJdbcTemplate.queryForObject(tenantSql, map, Integer.class);

        map.put("tenantId", tenantId);
        map.put("houseId", propertyAssignmentRequest.getHouseId());

        String sql = "UPDATE tenant SET house_id=:houseId WHERE tenant.id=:tenantId";

        notificationService.actionNotifications(tenantId, landlordId, Boolean.TRUE);

        parameterJdbcTemplate.update(sql, map);
    }


    public void inviteTenant(PropertyInviteRequest propertyInviteRequest) throws Exception {

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Map<String, Object> map = new HashMap<>();
        map.put("emailId", propertyInviteRequest.getEmailId());
        String sql = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";
        String deviceId = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);

        //save notification
        Notification notifications = new Notification(userSession.getUser().getEmailId(),
                propertyInviteRequest.getEmailId(),
                "New Property Request",
                "Please accept this property invitation",
                "landlordEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + propertyInviteRequest.getHouseId(),
                "image");
        notificationService.saveNotification(notifications);

        //Send Email
        emailService.sendPropertyInviteMail(propertyInviteRequest.getEmailId());

        //Send Push Notification
        if (deviceId != null) {

            JSONObject body = new JSONObject();
            // JsonArray registration_ids = new JsonArray();
            // body.put("registration_ids", registration_ids);
            body.put("to", deviceId);
            body.put("priority", "high");
            // body.put("dry_run", true);

            JSONObject notification = new JSONObject();
            notification.put("body", "Please accept this property invitation");
            notification.put("title", "New Property Request");
            // notification.put("icon", "myicon");

            JSONObject data = new JSONObject();
            data.put("landlordEmailId", userSession.getUser().getEmailId());
            data.put("houseId", propertyInviteRequest.getHouseId());

            body.put("notification", notification);
            body.put("data", data);

            HttpEntity<String> request = new HttpEntity<>(body.toString());

            CompletableFuture<FirebaseResponse> pushNotification = pushNotificationsService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            try {
                FirebaseResponse firebaseResponse = pushNotification.get();
                if (firebaseResponse.getSuccess() == 1) {
                    log.info("push notification sent ok!");
                } else {
                    log.error("error sending push notifications: " + firebaseResponse.toString());
                }
//            return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("Device Id can't be null");
        }
//        return new ResponseEntity<>("the push notification cannot be send.", HttpStatus.BAD_REQUEST);
    }

    public Boolean addContact(List<PropertyContact> propertyContactList, Long houseId) throws PropertyAddException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {
            List<Map<String, Object>> list = new ArrayList<>();

            for (PropertyContact propertyContact : propertyContactList) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("houseId", houseId);
                map1.put("contactType", ContactTypeMapper.getPropertyContactType(propertyContact.getContactType()));
                map1.put("name", propertyContact.getName());
                map1.put("address", propertyContact.getAddress());
                map1.put("contactNumber", propertyContact.getContactNumber());
                list.add(map1);
            }
            String sql = "INSERT INTO property_contact(house_id, contact_type_id, name, address, contact_number) VALUES (:houseId,:contactType,:name,:address,:contactNumber)";
            this.parameterJdbcTemplate.batchUpdate(sql, list.toArray(new Map[propertyContactList.size()]));

            return true;
        } else {
            throw new PropertyAddException("Property contact can only be added by Landlord");
        }

    }

    @Transactional
    public Boolean deleteProperty(PropertyDeleteRequest propertyDeleteRequest) throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("emailId", userSession.getUser().getEmailId());
            String landlordSql = "SELECT landlord.id landord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=:emailId";
            Integer landlordId = this.parameterJdbcTemplate.queryForObject(landlordSql, map1, Integer.class);
            map1.put("propertyId", propertyDeleteRequest.getPropertyId());
            map1.put("houseId", propertyDeleteRequest.getHouseId());
            map1.put("landlordId", landlordId);
            String sql = "SELECT property.id property_id FROM property INNER JOIN house ON property.id = house.property_id WHERE property.landlord_id=:landlordId AND property_id=:propertyId AND house.id=:houseId";
            String sqlUpdate = "UPDATE tenant SET house_id=NULL WHERE house_id=:houseId";
            String sqlDeleteTenantContact = "DELETE FROM tenant_contact WHERE house_id=:houseId";
            String sqlDeleteContact = "DELETE FROM property_contact WHERE house_id=:houseId";
            String sqlDeleteHouse = "DELETE FROM house WHERE id=:houseId";
            String sqlDeleteProperty = "DELETE FROM property WHERE id=:propertyId";
            try {
                this.parameterJdbcTemplate.queryForObject(sql, map1, Integer.class);
                this.parameterJdbcTemplate.update(sqlUpdate, map1);
                this.parameterJdbcTemplate.update(sqlDeleteTenantContact, map1);
                this.parameterJdbcTemplate.update(sqlDeleteContact, map1);
                this.parameterJdbcTemplate.update(sqlDeleteHouse, map1);
                this.parameterJdbcTemplate.update(sqlDeleteProperty, map1);
            } catch (Exception e) {
                throw new Exception("Property doesn't belongs to Landlord");
            }
            return true;
        } else {
            throw new Exception("Property can only be deleted by Landlord");
        }
    }
}
