package za.co.dladle.service;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.exception.UseAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.User;
import za.co.dladle.model.UserRegisterRequest;
import za.co.dladle.model.UserType;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 11/13/2016.
 */
@Service
public class UserServiceUtility {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate parameterJdbcTemplate;

    public User findUserByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT * FROM user_dladle WHERE emailid=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId}, (rs, rowNum) ->
                    new User(rs.getString("emailid")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User Doesn't exist");
        }
    }


    public User findUserByEmailAndPassword(String emailId, String password) throws UserNotFoundException {

        try {
            String sql = "SELECT * FROM user_dladle WHERE emailid=? AND password=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId, password}, (rs, rowNum) ->
                    new User(rs.getString("emailid"), rs.getString("password")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User Doesn't exist");
        }
    }

    public void updateUserPassword(String emailId, String password) {
        String sql = " UPDATE user_dladle SET password=? WHERE emailid=?";
        this.jdbcTemplate.update(sql, password, emailId);
    }


    public void userRegistration(UserRegisterRequest user) throws UseAlreadyExistsException {

        Map<String, Object> map = new HashMap<>();

        String hashedPassword = Hashing.sha512().hashString(user.getPassword(), Charset.defaultCharset()).toString();

        map.put("address", user.getAddress());
        map.put("serviceId", user.getBusinessType().getId());
        map.put("businessName", user.getBusinessName());
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("idNumber", user.getIdentityNumber());
        map.put("emailId", user.getEmailId());

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("emailId", user.getEmailId())
                .addValue("password", hashedPassword)
                .addValue("userType", user.getUserType().getId())
                .addValue("verified", false);

        String countSql = "SELECT COUNT(emailid) FROM user_dladle WHERE emailid=:emailId";

        Integer countEmail = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

        if (countEmail == 0) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            String UserSql = "INSERT INTO user_dladle (emailid, password, user_type_id, verified) VALUES (:emailId,:password,:userType,:verified)";

            this.parameterJdbcTemplate.update(UserSql, mapSqlParameterSource, keyHolder, new String[]{"id"});

            System.out.println(keyHolder.getKey());
            map.put("userId", keyHolder.getKey());

            if (user.getUserType().equals(UserType.LANDLORD)) {
                String LandlordSql = "INSERT INTO landlord(first_name, last_name, identity_number,user_id) VALUES (:firstName,:lastName,:idNumber,:userId)";
                this.parameterJdbcTemplate.update(LandlordSql, map);
            }
            if (user.getUserType().equals(UserType.TENANT)) {
                String TenantSql = "INSERT INTO tenant (first_name, last_name, identity_number,user_id) VALUES (:firstName,:lastName,:idNumber,:userId)";
                this.parameterJdbcTemplate.update(TenantSql, map);
            }
            if (user.getUserType().equals(UserType.VENDOR)) {
                String VendorSql = "INSERT INTO vendor (business_name, business_address, user_id,service_type_id) VALUES (:businessName,:address,:userId,:serviceId)";
                this.parameterJdbcTemplate.update(VendorSql, map);
            }

        } else {
            throw new UseAlreadyExistsException("User already Exists");
        }
    }
}