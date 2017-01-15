package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by prady on 1/14/2017.
 */
@Service
public class WelcomeService {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public String welcome() {
        String sql = "SELECT value FROM test_database";
        return jdbcTemplate.queryForObject(sql, new HashMap<>(), String.class);
    }
}
