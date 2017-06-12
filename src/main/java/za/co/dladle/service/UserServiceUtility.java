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
import za.co.dladle.exception.OtpMismatchException;
import za.co.dladle.exception.UseAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.exception.UserVerificationCodeNotMatchException;
import za.co.dladle.mapper.UserTypeMapper;
import za.co.dladle.model.User;
import za.co.dladle.entity.UserRegisterRequest;
import za.co.dladle.model.UserType;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 11/13/2016.
 */
@Service
public class UserServiceUtility {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NotificationServiceSendGridImpl notificationServiceSendGridImpl;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    //------------------------------------------------------------------------------------------------------------------
    //Find User By Email Id
    //------------------------------------------------------------------------------------------------------------------
    User findUserByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT * FROM user_dladle WHERE emailid=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase()}, (rs, rowNum) ->
                    new User(rs.getString("emailId")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User doesn't exist");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Find User Id By Email Id
    //------------------------------------------------------------------------------------------------------------------
    Long findUserIdByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT id FROM user_dladle WHERE emailid=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase()}, (rs, rowNum) -> rs.getLong("id"));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User doesn't exist");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Find User By Email Id and Password
    //------------------------------------------------------------------------------------------------------------------
    User findUserByEmailAndPassword(String emailId, String password) throws UserNotFoundException {

        try {
            String sql = "SELECT * FROM user_dladle INNER JOIN user_type ON user_dladle.user_type_id = user_type.id WHERE emailid=? AND password=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase(), password}, (rs, rowNum) ->
                    new User(rs.getString("emailId"),
                            null,
                            rs.getBoolean("verified"),
                            UserType.valueOf(rs.getString("name").toUpperCase()),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("id_number"),
                            rs.getString("cell_number"),
                            rs.getString("profile_picture")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Username or password is wrong. Please check and login again");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Find User By Email Id and Password
    //------------------------------------------------------------------------------------------------------------------
    User findUserDetailsByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT * FROM user_dladle INNER JOIN user_type ON user_dladle.user_type_id = user_type.id WHERE emailid=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase()}, (rs, rowNum) ->
                    new User(rs.getString("emailId"),
                            rs.getBoolean("verified"),
                            UserType.valueOf(rs.getString("name").toUpperCase()),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("id_number"),
                            rs.getString("cell_number"),
                            rs.getString("profile_picture")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User Not Found");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update User Password
    //------------------------------------------------------------------------------------------------------------------
    void updateUserPassword(String emailId, String password) {

        Map<String, Object> map = new HashMap<>();

        map.put("password", password);
        map.put("emailId", emailId.toLowerCase());

        String sql = " UPDATE user_dladle SET password=:password WHERE emailid=:emailId";
        this.parameterJdbcTemplate.update(sql, map);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update User OTP
    //------------------------------------------------------------------------------------------------------------------
    void updateUserOtp(String emailId, int otp) {

        Map<String, Object> map = new HashMap<>();

        map.put("otp", otp);
        map.put("emailId", emailId.toLowerCase());

        String sql = " UPDATE user_dladle SET otp=:otp WHERE emailid=:emailId";
        this.parameterJdbcTemplate.update(sql, map);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update User password if it matches OTP
    //------------------------------------------------------------------------------------------------------------------
    void updateUserPasswordWithOtp(String emailId, String password, int otp) throws OtpMismatchException {

        Map<String, Object> map = new HashMap<>();

        map.put("otp", otp);
        map.put("emailId", emailId.toLowerCase());
        map.put("password", password);

        String sql = " UPDATE user_dladle SET password=:password WHERE emailid=:emailId AND otp=:otp";
        int rows = this.parameterJdbcTemplate.update(sql, map);
        if (rows == 1) {
            String sqlUpdate = " UPDATE user_dladle SET otp=NULL WHERE emailid=:emailId AND otp=:otp AND password=:password";
            this.parameterJdbcTemplate.update(sqlUpdate, map);
        } else {
            throw new OtpMismatchException("OTP doesn't match");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Register
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    int userRegistration(UserRegisterRequest user, String hashedCode) throws UseAlreadyExistsException {

        String hashedPassword = Hashing.sha512().hashString(user.getPassword(), Charset.defaultCharset()).toString();

        Map<String, Object> map = new HashMap<>();
        map.put("emailId", user.getEmailId().toLowerCase());

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("emailId", user.getEmailId())
                .addValue("password", hashedPassword)
                .addValue("userType", UserTypeMapper.getUserType(user.getUserType()))
                .addValue("verified", false)
                .addValue("verifyCode", hashedCode)
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("idNumber", user.getIdentityNumber());


        String countSql = "SELECT COUNT(emailid) FROM user_dladle WHERE emailid=:emailId";

        Integer countEmail = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

        int rowsUpdated = 0;

        if (countEmail == 0) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            String UserSql = "INSERT INTO user_dladle (emailid, password, user_type_id, verified,verification_code,first_name,last_name,id_number) VALUES (:emailId,:password,:userType,:verified,:verifyCode,:firstName,:lastName,:idNumber)";

            this.parameterJdbcTemplate.update(UserSql, mapSqlParameterSource, keyHolder, new String[]{"id"});

            System.out.println(keyHolder.getKey());
            map.put("userId", keyHolder.getKey());

            if (user.getUserType().equals(UserType.LANDLORD)) {
                String LandlordSql = "INSERT INTO landlord(user_id) VALUES (:userId)";
                rowsUpdated = this.parameterJdbcTemplate.update(LandlordSql, map);
            }
            if (user.getUserType().equals(UserType.TENANT)) {
                String TenantSql = "INSERT INTO tenant (user_id) VALUES (:userId)";
                rowsUpdated = this.parameterJdbcTemplate.update(TenantSql, map);
            }
            if (user.getUserType().equals(UserType.VENDOR)) {
                String VendorSql = "INSERT INTO vendor (user_id) VALUES (:userId)";
                rowsUpdated = this.parameterJdbcTemplate.update(VendorSql, map);
            }
            return rowsUpdated;
        } else {
            throw new UseAlreadyExistsException("User already Exists");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update User Status based on Verification
    //------------------------------------------------------------------------------------------------------------------
    void updateUserVerification(String emailId, String verificationCode) throws UserVerificationCodeNotMatchException {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("emailId", emailId.toLowerCase())
                .addValue("verifyCode", verificationCode);
        try {
            String sql = "SELECT id FROM user_dladle WHERE emailid=:emailId AND verification_code=:verifyCode";
            this.parameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, (rs, rowNum) -> rs.getLong("id"));

            String sqlUpdate = "UPDATE user_dladle SET verified=TRUE WHERE emailid=:emailId AND verification_code=:verifyCode";
            this.parameterJdbcTemplate.update(sqlUpdate, mapSqlParameterSource);
            notificationServiceSendGridImpl.sendWelcomeMail(emailId);
        } catch (Exception e) {
            throw new UserVerificationCodeNotMatchException("Either EmailId or Verification Code for User doesn't match");
        }
    }
}