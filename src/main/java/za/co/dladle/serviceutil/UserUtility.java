package za.co.dladle.serviceutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 11/13/2016.
 */
@Service
public class UserUtility {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //------------------------------------------------------------------------------------------------------------------
    //Find User By Email Id
    //------------------------------------------------------------------------------------------------------------------
    public User findUserByEmail(String emailId) throws UserNotFoundException {

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
    public Long findUserIdByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT id FROM user_dladle WHERE emailid=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase()}, (rs, rowNum) -> rs.getLong("id"));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User doesn't exist");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Find Tenant Id By Email Id
    //------------------------------------------------------------------------------------------------------------------
    public Long findTenantIdByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT tenant.id tenant_id FROM user_dladle INNER JOIN tenant ON user_dladle.id = tenant.user_id WHERE emailid=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase()}, (rs, rowNum) -> rs.getLong("tenant_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User doesn't exist");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Find Landlord Id By Email Id
    //------------------------------------------------------------------------------------------------------------------
    public Long findLandlordIdByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT landlord.id landlord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase()}, (rs, rowNum) -> rs.getLong("landlord_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User doesn't exist");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Find Vendor Id By Email Id
    //------------------------------------------------------------------------------------------------------------------
    public Long findVendorIdByEmail(String emailId) throws UserNotFoundException {

        try {
            String sql = "SELECT vendor.id vendor_id FROM user_dladle INNER JOIN vendor ON user_dladle.id = vendor.user_id WHERE emailid=?";
            return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase()}, (rs, rowNum) -> rs.getLong("vendor_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User doesn't exist");
        }
    }

    public String getNameByEmailId(String emailId) {
        String getUserSql = "SELECT first_name FROM user_dladle WHERE emailid=:emailId";
        return this.jdbcTemplate.queryForObject(getUserSql, new Object[]{emailId.toLowerCase()}, String.class);
    }
}