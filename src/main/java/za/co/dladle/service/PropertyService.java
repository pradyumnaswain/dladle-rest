package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.*;
import za.co.dladle.exception.PropertyAddException;
import za.co.dladle.exception.PropertyAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.mapper.DocumentTypeMapper;
import za.co.dladle.mapper.PlaceTypeMapper;
import za.co.dladle.model.DocumentType;
import za.co.dladle.model.Property;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.thirdparty.FileManagementServiceCloudinaryImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private FileManagementServiceCloudinaryImpl fileManagementServiceCloudinary;

    @Autowired
    private UserUtility userUtility;

    //------------------------------------------------------------------------------------------------------------------
    //Insert Property
    //------------------------------------------------------------------------------------------------------------------

    @Transactional
    public PropertyAddResponse addProperty(PropertyAddRequest property) throws PropertyAlreadyExistsException, PropertyAddException, IOException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {
            String email = userSession.getUser().getEmailId();

            Map<String, Object> map = new HashMap<>();
            map.put("emailId", email);
            String landlordSql = "SELECT landlord.id landord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=:emailId";
            Integer landlordId = this.parameterJdbcTemplate.queryForObject(landlordSql, map, Integer.class);

            map.put("landlordId", landlordId);
            map.put("address", property.getAddress());

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("landlordId", landlordId)
                    .addValue("address", property.getAddress())
                    .addValue("addressLatitude", property.getAddressLatitude())
                    .addValue("addressLongitude", property.getAddressLongitude())
                    .addValue("PlaceType", PlaceTypeMapper.getPlaceType(property.getPlaceType()))
                    .addValue("complexName", property.getComplexName())
                    .addValue("unitNumber", property.getUnitNo())
                    .addValue("isEstate", property.isInEstate())
                    .addValue("estateName", property.getEstateName())
                    .addValue("addDate", LocalDate.now());

            String countSql = "SELECT COUNT(address) FROM property WHERE address=:address AND landlord_id=:landlordId";

            Integer countAddress = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

            if (countAddress == 0) {

                if (property.getPlaceImage() != null) {
                    if (!property.getPlaceImage().equalsIgnoreCase("")) {

//                        String imagePath = fileManagementServiceCloudinary.upload(property.getPlaceImage());
                        mapSqlParameterSource.addValue("imgUrl", null);
                    } else {
                        mapSqlParameterSource.addValue("imgUrl", null);
                    }
                } else {
                    mapSqlParameterSource.addValue("imgUrl", null);
                }
                KeyHolder propertyId = new GeneratedKeyHolder();

                String PropertySql = "INSERT INTO property (landlord_id, address,address_latitude,address_longitude, place_type_id, complex_name, unit_number, image_url, isEstate, estate_name,property_add_date) VALUES (:landlordId, :address,:addressLatitude,:addressLongitude, :PlaceType,:complexName,:unitNumber,:imgUrl,:isEstate, :estateName,:addDate)";

                this.parameterJdbcTemplate.update(PropertySql, mapSqlParameterSource, propertyId, new String[]{"id"});

                mapSqlParameterSource.addValue("propertyId", propertyId.getKey().longValue());

                KeyHolder houseId = new GeneratedKeyHolder();
                String sqlHouse = "INSERT INTO house(property_id) VALUES (:propertyId)";
                this.parameterJdbcTemplate.update(sqlHouse, mapSqlParameterSource, houseId, new String[]{"id"});

                PropertyAddResponse propertyAddResponse = new PropertyAddResponse();

                propertyAddResponse.setHouseId(houseId.getKey().longValue());
                propertyAddResponse.setPropertyId(propertyId.getKey().longValue());
                return propertyAddResponse;
            } else {
                throw new PropertyAlreadyExistsException("Property already Exists");
            }
        } else {
            throw new PropertyAddException("Property can only be added by Landlord");
        }
    }

    public List<Property> listProperties() throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {
            String email = userSession.getUser().getEmailId();

            List<Property> propertyList = new ArrayList<>();

            Map<String, Object> map = new HashMap<>();
            map.put("emailId", email);
            String landlordSql = "SELECT property.*,property.id property_id,house.*, house.id house_id,place_type.name place_name FROM property " +
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
                property.setContactsCount(rs.getInt("contacts_count"));
                property.setTenantsCount(rs.getInt("tenants_count"));
                property.setHome(rs.getBoolean("is_home"));
                property.setActiveJob(rs.getBoolean("active_job"));

                try {
                    Long userId = userUtility.findUserIdByEmail(email);
                    map.put("userId", userId);
                    map.put("houseId", property.getHouseId());
                    String sqlCount = "SELECT count FROM notification_count " +
                            " WHERE user_id=:userId AND house_id=:houseId";
                    try {
                        Integer count = this.parameterJdbcTemplate.queryForObject(sqlCount, map, Integer.class);
                        property.setNotificationsCount(count);
                    } catch (Exception e) {
                        property.setNotificationsCount(0);
                    }
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }

                List<PropertyContactView> contacts = new ArrayList<>();
                map.put("houseId", property.getHouseId());
                String sql = "SELECT property_contact.*,property_contact.name property_conact_name, contact_type.name contact_type_name FROM property_contact INNER JOIN contact_type ON property_contact.contact_type_id = contact_type.id WHERE house_id=:houseId";
                this.parameterJdbcTemplate.query(sql, map, (rs1, rowNum1) -> {
                    PropertyContactView propertyContact = new PropertyContactView();
                    propertyContact.setPropertyContactId(rs.getLong("id"));
                    propertyContact.setAddress(rs1.getString("address"));
                    propertyContact.setName(rs1.getString("property_conact_name"));
                    propertyContact.setContactType(rs1.getString("contact_type_name"));
                    propertyContact.setContactNumber(rs1.getString("contact_number"));
                    contacts.add(propertyContact);
                    return propertyContact;
                });
                property.setPropertyContactList(contacts);

                List<TenantView> tenantList = new ArrayList<>();
                map.put("houseId", property.getHouseId());
                String sql1 = "SELECT * FROM user_dladle INNER JOIN tenant ON tenant.user_id = user_dladle.id WHERE house_id=:houseId";
                this.parameterJdbcTemplate.query(sql1, map, (rs1, rowNum1) -> {
                    TenantView tenantView = new TenantView(
                            rs1.getString("emailid"),
                            rs1.getString("first_name"),
                            rs1.getString("last_name"),
                            rs1.getString("id_number"),
                            rs1.getString("cell_number"),
                            rs1.getString("profile_picture")
                    );

                    tenantList.add(tenantView);
                    return tenantView;
                });
                property.setTenantList(tenantList);

                if (tenantList.isEmpty()) {
                    property.setLeaseStatus(false);
                } else {
                    property.setLeaseStatus(true);
                }

                propertyList.add(property);

                return property;
            });
            return propertyList;
        } else if (userSession.getUser().getUserType().eqTENANT()) {
            String email = userSession.getUser().getEmailId();

            List<Property> propertyList = new ArrayList<>();

            Map<String, Object> map = new HashMap<>();
            map.put("emailId", email);
            String landlordSql = "SELECT property.*,property.id property_id,house.*, house.id house_id,place_type.name place_name FROM property " +
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
                property.setActiveJob(rs.getBoolean("active_job"));

                try {
                    Long userId = userUtility.findUserIdByEmail(email);
                    map.put("userId", userId);
                    map.put("houseId", property.getHouseId());
                    String sqlCount = "SELECT count FROM notification_count WHERE user_id=:userId AND house_id=:houseId";

                    try {
                        Integer count = this.parameterJdbcTemplate.queryForObject(sqlCount, map, Integer.class);
                        property.setNotificationsCount(count);
                    } catch (Exception e) {
                        property.setNotificationsCount(0);
                    }
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }

                List<PropertyContactView> contacts = new ArrayList<>();
                map.put("houseId", property.getHouseId());
                String sql = "SELECT property_contact.*,property_contact.name property_conact_name, contact_type.name contact_type_name FROM property_contact INNER JOIN contact_type ON property_contact.contact_type_id = contact_type.id WHERE house_id=:houseId";
                this.parameterJdbcTemplate.query(sql, map, (rs1, rowNum1) -> {
                    PropertyContactView propertyContact = new PropertyContactView();
                    propertyContact.setPropertyContactId(rs.getLong("id"));
                    propertyContact.setAddress(rs1.getString("address"));
                    propertyContact.setName(rs1.getString("property_conact_name"));
                    propertyContact.setContactType(rs1.getString("contact_type_name"));
                    propertyContact.setContactNumber(rs1.getString("contact_number"));
                    contacts.add(propertyContact);
                    return propertyContact;
                });
                property.setPropertyContactList(contacts);

                map.put("propertyId", property.getPropertyId());
                String sql1 = "SELECT * FROM user_dladle INNER JOIN landlord ON landlord.user_id = user_dladle.id " +
                        "INNER JOIN property ON landlord.id = property.landlord_id " +
                        "WHERE property.id=:propertyId";
                LandlordView landlordView = this.parameterJdbcTemplate.queryForObject(sql1, map, (rs1, rowNum1) -> new LandlordView(
                        rs1.getString("emailid"),
                        rs1.getString("first_name"),
                        rs1.getString("last_name"),
                        rs1.getString("id_number"),
                        rs1.getString("cell_number"),
                        rs1.getString("profile_picture")
                ));
                property.setLandlord(landlordView);

                propertyList.add(property);

                return property;
            });
            return propertyList;
        } else {
            throw new PropertyAddException("Property Unavailable");
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

    public List<TenantView> listTenantsOnProperty(long houseId) {
        Map<String, Object> map = new HashMap<>();
        map.put("houseId", houseId);

        List<TenantView> tenantViews = new ArrayList<>();
        String sql = "SELECT * FROM tenant INNER JOIN user_dladle ON tenant.user_id = user_dladle.id WHERE house_id=:houseId";
        this.parameterJdbcTemplate.query(sql, map, (rs, rowNum) -> {
            TenantView tenantView = new TenantView(
                    rs.getString("emailid"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("id_number"),
                    rs.getString("cell_number"),
                    rs.getString("profile_picture")
            );

            tenantViews.add(tenantView);
            return tenantView;
        });
        return tenantViews;
    }

    public void setHome(HomeRequest homeRequest) throws PropertyAddException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {

            Map<String, Object> map1 = new HashMap<>();
            map1.put("houseId", homeRequest.getHouseId());
            String sql = "UPDATE house SET is_home=TRUE WHERE house.id=:houseId";
            this.parameterJdbcTemplate.update(sql, map1);

        } else {
            throw new PropertyAddException("Property home can only be set by Landlord");
        }
    }

    public String uploadPropertyPic(PropertyImageUploadRequest propertyImageUploadRequest) throws UserNotFoundException, IOException {

        String imageUrl = fileManagementServiceCloudinary.upload(propertyImageUploadRequest.getBase64Image(), propertyImageUploadRequest.getFileName());
        Map<String, Object> map = new HashMap<>();
        map.put("profilePicture", imageUrl);
        map.put("propertyId", propertyImageUploadRequest.getPropertyId());
        String sql = "UPDATE property SET image_url=:profilePicture WHERE id=:propertyId;";
        this.parameterJdbcTemplate.update(sql, map);

        return imageUrl;
    }

    public void addDocuments(PropertyAddDocumentsRequest propertyAddDocumentsRequest) throws Exception {
        List<PropertyAddDocument> documentList = propertyAddDocumentsRequest.getImageList();
        ExecutorService executorService = Executors.newFixedThreadPool(documentList.size());
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        List<Map<String, Object>> list = new ArrayList<>();
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        if (userSession.getUser().getUserType().eqTENANT()) {

            Long tenantId = userUtility.findTenantIdByEmail(userSession.getUser().getEmailId());
            for (PropertyAddDocument document : documentList) {
                completionService.submit(() -> {

                    Map<String, Object> map = new HashMap<>();

                    String documentUrl = fileManagementServiceCloudinary.upload(document.getBase64Document(), document.getFileName());

                    map.put("imageUrl", documentUrl);
                    map.put("documentType", DocumentTypeMapper.getDocumentType(document.getDocumentType()));
                    map.put("tenantId", tenantId);
                    map.put("addDate", LocalDate.now());

                    list.add(map);
                    return null;
                });
            }
            for (PropertyAddDocument document : documentList) {
                completionService.take().get();
            }
            String sql = "INSERT INTO tenant_property_documents (tenant_id, url,document_type,add_date) VALUES (:tenantId, :imageUrl,:documentType,:addDate)";
            this.parameterJdbcTemplate.batchUpdate(sql, list.toArray(new Map[documentList.size()]));
        } else {
            throw new Exception("You are not authorised to upe this functionality");
        }
    }

    public List<PropertyViewDocuments> viewDocuments() throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        if (userSession.getUser().getUserType().eqTENANT()) {
            List<PropertyViewDocuments> propertyViewDocuments = new ArrayList<>();

            Long tenantId = userUtility.findTenantIdByEmail(userSession.getUser().getEmailId());

            Map<String, Object> map = new HashMap<>();
            map.put("tenantId", tenantId);

            String sql = "SELECT * FROM tenant_property_documents WHERE tenant_id=:tenantId AND valid=TRUE ";

            this.parameterJdbcTemplate.query(sql, map, (rs, rowNum) -> {

                PropertyViewDocuments propertyViewDocument = new PropertyViewDocuments();
                propertyViewDocument.setDocumentType(DocumentTypeMapper.getDocumentType(rs.getInt("document_type")));
                propertyViewDocument.setDocumentUrl(rs.getString("url"));
                propertyViewDocument.setDate(rs.getString("add_date"));
                propertyViewDocuments.add(propertyViewDocument);
                return propertyViewDocument;
            });

            return propertyViewDocuments;
        } else {
            throw new Exception("You are not authorised for this operation");
        }
    }
}
