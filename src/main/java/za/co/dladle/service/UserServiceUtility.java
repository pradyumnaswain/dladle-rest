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
import za.co.dladle.exception.UserVerificationCodeNotMatchException;
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
                    new User(rs.getString("emailId")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User Doesn't exist");
        }
    }


    public User findUserByEmailAndPassword(String emailId, String password) throws UserNotFoundException {

        try {
            String sql = "SELECT * FROM user_dladle INNER JOIN user_type ON user_dladle.user_type_id = user_type.id WHERE emailid=? AND password=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId, password}, (rs, rowNum) ->
                    new User(rs.getString("emailId"), rs.getString("password"), rs.getBoolean("verified"), UserType.valueOf(rs.getString("name").toUpperCase())));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Username or password is wrong. Please check and login again");
        }
    }

    public void updateUserPassword(String emailId, String password) {

        Map<String, Object> map = new HashMap<>();

        map.put("password", password);
        map.put("emailId", emailId);

        String sql = " UPDATE user_dladle SET password=:password WHERE emailid=:emailId";
        this.parameterJdbcTemplate.update(sql, map);
    }


    public int userRegistration(UserRegisterRequest user, String hashedCode) throws UseAlreadyExistsException {


        String hashedPassword = Hashing.sha512().hashString(user.getPassword(), Charset.defaultCharset()).toString();

        Map<String, Object> map = new HashMap<>();
        map.put("emailId", user.getEmailId());

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("emailId", user.getEmailId())
                .addValue("password", hashedPassword)
                .addValue("userType", user.getUserType().getId())
                .addValue("verified", false)
                .addValue("verifyCode", hashedCode);

        String countSql = "SELECT COUNT(emailid) FROM user_dladle WHERE emailid=:emailId";

        Integer countEmail = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

        int rowsUpdated = 0;

        if (countEmail == 0) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            String UserSql = "INSERT INTO user_dladle (emailid, password, user_type_id, verified,verification_code) VALUES (:emailId,:password,:userType,:verified,:verifyCode)";

            this.parameterJdbcTemplate.update(UserSql, mapSqlParameterSource, keyHolder, new String[]{"id"});

            System.out.println(keyHolder.getKey());
            map.put("userId", keyHolder.getKey());

            if (user.getUserType().equals(UserType.LANDLORD)) {
                map.put("firstName", user.getFirstName());
                map.put("lastName", user.getLastName());
                map.put("idNumber", user.getIdentityNumber());
                String LandlordSql = "INSERT INTO landlord(first_name, last_name, identity_number,user_id) VALUES (:firstName,:lastName,:idNumber,:userId)";
                rowsUpdated = this.parameterJdbcTemplate.update(LandlordSql, map);
            }
            if (user.getUserType().equals(UserType.TENANT)) {
                map.put("firstName", user.getFirstName());
                map.put("lastName", user.getLastName());
                map.put("idNumber", user.getIdentityNumber());
                String TenantSql = "INSERT INTO tenant (first_name, last_name, identity_number,user_id) VALUES (:firstName,:lastName,:idNumber,:userId)";
                rowsUpdated = this.parameterJdbcTemplate.update(TenantSql, map);
            }
            if (user.getUserType().equals(UserType.VENDOR)) {
                map.put("address", user.getAddress());
                map.put("serviceId", user.getBusinessType().getId());
                map.put("businessName", user.getBusinessName());

                String VendorSql = "INSERT INTO vendor (business_name, business_address, user_id,service_type_id) VALUES (:businessName,:address,:userId,:serviceId)";
                rowsUpdated = this.parameterJdbcTemplate.update(VendorSql, map);
            }
            return rowsUpdated;
        } else {
            throw new UseAlreadyExistsException("User already Exists");
        }
    }

    public void updateUserVerification(String emailId, String verificationCode) throws UserVerificationCodeNotMatchException {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("emailId", emailId)
                .addValue("verifyCode", verificationCode);
        try {
            String sql = "SELECT id FROM user_dladle WHERE emailid=:emailId AND verification_code=:verifyCode";
            this.parameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, (rs, rowNum) -> rs.getLong("id"));

            String sqlUpdate = "UPDATE user_dladle SET verified=TRUE WHERE emailid=:emailId AND verification_code=:verifyCode";
            this.parameterJdbcTemplate.update(sqlUpdate, mapSqlParameterSource);
        } catch (Exception e) {
            throw new UserVerificationCodeNotMatchException("Either EmailId or Verification Code for User doesn't match");
        }
    }
}