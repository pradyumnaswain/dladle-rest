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
import za.co.dladle.entity.PropertyAddResponse;
import za.co.dladle.exception.PropertyAlreadyExistsException;
import za.co.dladle.session.UserSession;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jugal on 05/02/2017.
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
    public PropertyAddResponse propertyRegistration(PropertyAddRequest property) throws PropertyAlreadyExistsException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String email = userSession.getUser().getEmailId();

        Map<String, Object> map = new HashMap<>();
        map.put("address", property.getAddress());
        map.put("emailId", email);

        String getUserSql = "SELECT landlord.id landord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=:emailId";
        Integer userId = this.parameterJdbcTemplate.queryForObject(getUserSql, map, Integer.class);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("landlordId", userId)
                .addValue("address", property.getAddress())
                .addValue("PlaceType", property.getPlaceType().getId())
                .addValue("complexName", property.getComplexName())
                .addValue("unitNumber", property.getUnitNumber())
                .addValue("imgUrl", property.getImgUrl())
                .addValue("isEstate", property.isEstate())
                .addValue("estateName", property.getEstateName());
        ;


        String countSql = "SELECT COUNT(address) FROM property WHERE address=:address";

        Integer countAddress = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

        int rowsUpdated = 0;

        if (countAddress == 0) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            String PropertySql = "INSERT INTO property (landlord_id, address, place_type_id, complex_name, unit_number, image_url, isEstate, estate_name) VALUES (:landlordId, :address, :PlaceType,:complexName,:unitNumber,:imgUrl,:isEstate, :estateName)";

            this.parameterJdbcTemplate.update(PropertySql, mapSqlParameterSource, keyHolder, new String[]{"id"});

            System.out.println(keyHolder.getKey());

            PropertyAddResponse propertyAddResponse = new PropertyAddResponse();

            propertyAddResponse.setPropertyId(keyHolder.getKey().longValue());

            return propertyAddResponse;
        } else {
            throw new PropertyAlreadyExistsException("Property already Exists");
        }
    }

}
