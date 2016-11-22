package za.co.dladle.service;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
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


    public void userRegistration(String emailId, String password, Integer user_type, boolean verified){

            String countSql = "SELECT COUNT(emailid) FROM user_dladle where emailid=?";
            int countEmail = this.jdbcTemplate.update(countSql, new Object[] { emailId });
            if (countEmail == 0) {
                String UserSql = "INSERT INTO user_dladle " + "(emailid, password, user_type_id, verified) VALUES (?, ?, ?, ?)";
                this.jdbcTemplate.update(UserSql, new Object[] { emailId,password, user_type,verified});
            }
    }

}