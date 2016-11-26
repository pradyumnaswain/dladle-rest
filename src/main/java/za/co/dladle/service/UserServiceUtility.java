package za.co.dladle.service;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.exception.UseAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.User;
import za.co.dladle.model.UserRegisterRequest;

/**
 * Created by prady on 11/13/2016.
 */
@Service
public class UserServiceUtility {

    @Autowired
    JdbcTemplate jdbcTemplate;

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


    public void userRegistration(String firstName, String lastName, String emailId, String password, Integer user_type_id, String identityNumber, boolean verified, String business_address, String business_type) throws UseAlreadyExistsException {

        Integer countEmail;
        String countSql = "SELECT COUNT(emailid) FROM user_dladle WHERE emailid=?";
        countEmail = this.jdbcTemplate.queryForObject(countSql, new Object[]{emailId}, Integer.class);
        if (countEmail == 0) {
            if (user_type_id == 1){
                String LandlordSql = "INSERT INTO landlord " + "(first_name, last_name, emailid, password, identity_number) VALUES (?, ?, ?, ?, ?)";
            this.jdbcTemplate.update(LandlordSql,firstName,lastName,emailId,password,identityNumber);
            }
            if (user_type_id == 2){
                String TenantSql = "INSERT INTO tenant " + "(first_name, last_name, emailid, password, identity_number) VALUES (?, ?, ?, ?, ?)";
                this.jdbcTemplate.update(TenantSql,firstName,lastName,emailId,password,identityNumber);
            }
            if (user_type_id == 3){
                String VendorSql = "INSERT INTO vendor " + "(first_name, business_address, emailid, password, business_type ) VALUES (?, ?, ?, ?, ?)";
                this.jdbcTemplate.update(VendorSql,firstName,business_address,emailId,password,business_type);
            }
            String UserSql = "INSERT INTO user_dladle " + "(emailid, password, user_type_id, verified) VALUES (?, ?, ?, ?)";
            this.jdbcTemplate.update(UserSql,emailId,password,user_type_id,verified);

        } else {
            throw new UseAlreadyExistsException("User already Exists");
        }
    }
}