package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.User;

/**
 * Created by prady on 11/13/2016.
 */
@Service
public class UserServiceUtility {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User findUserByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT * FROM user_dladle WHERE email=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId}, (rs, rowNum) ->
                    new User(rs.getString("email")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User Doesn't exist");
        }
    }


    public User findUserByEmailAndPassword(String emailId, String password) throws UserNotFoundException {

        try {
            String sql = "SELECT * FROM user_dladle WHERE email=? AND password=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId, password}, (rs, rowNum) ->
                    new User(rs.getString("email"), rs.getString("password")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User Doesn't exist");
        }
    }

    public void updateUserPassword(String emailId, String password) {
        String sql = " UPDATE user_dladle SET password=? WHERE email=?";
        this.jdbcTemplate.update(sql, password, emailId);
    }
}