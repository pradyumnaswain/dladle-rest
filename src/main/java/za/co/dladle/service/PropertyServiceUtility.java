package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.PropertyAddRequest;
import za.co.dladle.entity.propertyUpdateRequest;
import za.co.dladle.exception.PropertyAlreadyExistsException;
import za.co.dladle.session.UserSession;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 310252258 on 05/02/2017.
 */

@Service
public class PropertyServiceUtility {
    @Autowired
    private HttpSession session;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate parameterJdbcTemplate;

    //------------------------------------------------------------------------------------------------------------------
    //Insert Property
    //------------------------------------------------------------------------------------------------------------------

    @Transactional
    public int propertyRegistration(PropertyAddRequest property) throws PropertyAlreadyExistsException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String email = userSession.getUser().getEmailId();

        Map<String, Object> map = new HashMap<>();
        map.put("address", property.getAddress());
        map.put("emailId", email);

        String  getUserSql= "SELECT id FROM user_dladle WHERE emailid=:emailId";
        Integer userId = this.parameterJdbcTemplate.queryForObject(getUserSql, map, Integer.class);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("landlordId", userId)
                .addValue("location", property.getLocation())
                .addValue("address", property.getAddress())
                .addValue("placeName", property.getPlaceName())
                .addValue("placeType", property.getPlaceType())
                .addValue("complexName", property.getComplexName())
                .addValue("unitNumber", property.getUnitNumber())
                .addValue("bedRoomType", property.getBedRoomType())
                .addValue("imgUrl", property.getImgUrl())
                .addValue("homeView", property.getHomeView());;


        String countSql = "SELECT COUNT(address) FROM property WHERE address=:address";

        Integer countAddress = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

        int rowsUpdated = 0;

        if (countAddress == 0) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            String PropertySql = "INSERT INTO property (landlord_id, location, address, place_name, place_type_id,complex_name,unit_number,bedroom_type_id,image_url,home_view_type_id) VALUES (:landlordId,  :location, :address, :placeName,:placeType,:complexName,:unitNumber,:bedRoomType,:imgUrl,:homeView)";

            this.parameterJdbcTemplate.update(PropertySql, mapSqlParameterSource, keyHolder, new String[]{"id"});

            System.out.println(keyHolder.getKey());
            map.put("propertyId", keyHolder.getKey());


            return rowsUpdated;
        } else {
            throw new PropertyAlreadyExistsException("Property already Exists");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update Property
    //------------------------------------------------------------------------------------------------------------------
    public int updateProperty(propertyUpdateRequest propertyUpdateRequest) {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String email = userSession.getUser().getEmailId();

        Map<String, Object> map = new HashMap<>();
        map.put("emailId", email);

        String  getUserSql= "SELECT id FROM user_dladle WHERE emailid=:emailId";
        Integer userId = this.parameterJdbcTemplate.queryForObject(getUserSql, map, Integer.class);


        map.put("landlordId", userId);
        map.put("location", propertyUpdateRequest.getLocation());
        map.put("address",propertyUpdateRequest.getAddress());
        map.put("placeName",propertyUpdateRequest.getPlaceName() );
        map.put("placeTypeId", propertyUpdateRequest.getPlaceType() );
        map.put("complexName", propertyUpdateRequest.getComplexName());
        map.put("unitNumber", propertyUpdateRequest.getUnitNumber());
        map.put("bedRoomTypeId", propertyUpdateRequest.getBedRoomType());
        map.put("imageUrl", propertyUpdateRequest.getImgUrl());
        map.put("homeViewTypeId", propertyUpdateRequest.getHomeView() );

        String sql = " UPDATE property SET  location=:location, address=:address, place_name=:placeName, place_type_id=:placeTypeId, complex_name=:complexName, unit_number=:unitNumber, bedroom_type_id=:bedRoomTypeId,image_url =:imageUrl, home_view_type_id=:homeViewTypeId WHERE landlord_id=:landlordId";
        return this.parameterJdbcTemplate.update(sql, map);
    }
}
