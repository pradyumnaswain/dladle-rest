package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.PropertyAddRequest;
import za.co.dladle.entity.PropertyAddResponse;
import za.co.dladle.exception.PropertyAddException;
import za.co.dladle.exception.PropertyAlreadyExistsException;
import za.co.dladle.mapper.ContactTypeMapper;
import za.co.dladle.mapper.PlaceTypeMapper;
import za.co.dladle.model.PropertyContact;
import za.co.dladle.session.UserSession;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jugal on 05/02/2017.
 */

@Service
public class PropertyService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    //------------------------------------------------------------------------------------------------------------------
    //Insert Property
    //------------------------------------------------------------------------------------------------------------------

    @Transactional
    public PropertyAddResponse addProperty(PropertyAddRequest property) throws PropertyAlreadyExistsException, PropertyAddException {
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
                    .addValue("imgUrl", property.getPlaceImage())
                    .addValue("isEstate", property.isInEstate())
                    .addValue("estateName", property.getEstateName());

            String countSql = "SELECT COUNT(address) FROM property WHERE address=:address AND landlord_id=:landlordId";

            Integer countAddress = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

            if (countAddress == 0) {

                KeyHolder propertyId = new GeneratedKeyHolder();

                String PropertySql = "INSERT INTO property (landlord_id, address, place_type_id, complex_name, unit_number, image_url, isEstate, estate_name) VALUES (:landlordId, :address, :PlaceType,:complexName,:unitNumber,:imgUrl,:isEstate, :estateName)";

                this.parameterJdbcTemplate.update(PropertySql, mapSqlParameterSource, propertyId, new String[]{"id"});

                mapSqlParameterSource.addValue("propertyId", propertyId.getKey().longValue());

                List<Map<String, Object>> list = new ArrayList<>();

                for (PropertyContact propertyContact : property.getPropertyContactList()) {
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("propertyId", propertyId.getKey().longValue());
                    map1.put("contactType", ContactTypeMapper.getPropertyContactType(propertyContact.getContactType()));
                    map1.put("name", propertyContact.getName());
                    map1.put("address", propertyContact.getAddress());
                    map1.put("contactNumber", propertyContact.getContactNumber());
                    list.add(map1);
                }
                String sql = "INSERT INTO property_contact(property_id, contact_type_id, name, address, contact_number) VALUES (:propertyId,:contactType,:name,:address,:contactNumber)";
                this.parameterJdbcTemplate.batchUpdate(sql, list.toArray(new Map[property.getPropertyContactList().size()]));

                KeyHolder houseId = new GeneratedKeyHolder();

                String sqlHouse = "INSERT INTO house(property_id) VALUES (:propertyId)";
                this.parameterJdbcTemplate.update(sqlHouse, mapSqlParameterSource, houseId, new String[]{"id"});

                PropertyAddResponse propertyAddResponse = new PropertyAddResponse();

                propertyAddResponse.setPropertyId(propertyId.getKey().longValue());

                propertyAddResponse.setHouseId(houseId.getKey().longValue());

                return propertyAddResponse;
            } else {
                throw new PropertyAlreadyExistsException("Property already Exists");
            }
        } else {
            throw new PropertyAddException("Property can only be added by Landlord");
        }
    }

    public PropertyAddResponse listProperties() {
        return null;
    }
}
