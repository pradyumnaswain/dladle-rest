package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.RatingAddRequest;
import za.co.dladle.entity.RatingUpdateRequest;
import za.co.dladle.entity.RatingView;
import za.co.dladle.entity.RatingViewDetails;
import za.co.dladle.model.User;
import za.co.dladle.session.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 4/16/2017.
 */
@Service
public class RatingService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserServiceUtility utility;

    public RatingView viewRating() throws Exception {

        try {
            UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

            User user = userSession.getUser();

            Long userId = utility.findUserIdByEmail(user.getEmailId());

            Map<String, Long> map = new HashMap<>();

            map.put("userId", userId);

            String sql = "SELECT avg(value) AS rate,count(rating.id) AS count FROM rating WHERE rated_user=:userId";

            return jdbcTemplate.queryForObject(sql, map, (rs, rowNum) -> new RatingView(rs.getDouble("rate"), rs.getInt("count")));
        } catch (Exception e) {
            throw new Exception("Rate not available for above user");
        }
    }

    public Double viewRating(String emailId) throws Exception {

        Long userId = utility.findUserIdByEmail(emailId);

        Map<String, Long> map = new HashMap<>();

        map.put("userId", userId);

        String sql = "SELECT avg(value) AS rate FROM rating WHERE rated_user=:userId";

        return jdbcTemplate.queryForObject(sql, map, Double.class);
    }

    public List<RatingViewDetails> viewRatingDetails() throws Exception {
        try {
            UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

            User user = userSession.getUser();

            Long userId = utility.findUserIdByEmail(user.getEmailId());

            Map<String, Long> map = new HashMap<>();

            map.put("userId", userId);

            String sql = "SELECT * FROM rating INNER JOIN user_dladle ON rating.rating_user=user_dladle.id WHERE rated_user=:userId";

            return jdbcTemplate.query(sql, map, (rs, rowNum) -> new RatingViewDetails(rs.getDouble("value"), rs.getString("rating_comment"), rs.getString("first_name") + " " + rs.getString("last_name")));
        } catch (Exception e) {
            throw new Exception("Rate not available for above user");
        }
    }

    public List<RatingViewDetails> viewRatingDetails(String emailId) throws Exception {

        List<RatingViewDetails> ratingViewDetails = new ArrayList<>();
        Long userId = utility.findUserIdByEmail(emailId);

        Map<String, Long> map = new HashMap<>();

        map.put("userId", userId);

        String sql = "SELECT * FROM rating INNER JOIN user_dladle ON rating.rating_user=user_dladle.id WHERE rated_user=:userId";

        ratingViewDetails = jdbcTemplate.query(sql, map, (rs, rowNum) -> new RatingViewDetails(rs.getDouble("value"), rs.getString("rating_comment"), rs.getString("first_name") + " " + rs.getString("last_name")));
        return ratingViewDetails;
    }

    public boolean postRating(RatingAddRequest ratingAddRequest) throws Exception {
        try {
            UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

            User user = userSession.getUser();

            Long ratingUser = utility.findUserIdByEmail(user.getEmailId());
            Long ratedUser = utility.findUserIdByEmail(ratingAddRequest.getRatedUserEmailId());

            Map<String, Object> map = new HashMap<>();

            map.put("value", ratingAddRequest.getRate());
            map.put("ratingUser", ratingUser);
            map.put("ratedUser", ratedUser);
            map.put("ratingComment", ratingAddRequest.getComment());

            String sql = "INSERT INTO rating(value,rated_user,rating_user,rating_comment) VALUES (:value,:ratedUser,:ratingUser,:ratingComment)";

            int update = jdbcTemplate.update(sql, map);

            return update == 1;
        } catch (Exception e) {
            throw new Exception("Unable to post rating");
        }
    }

    public boolean updateRating(RatingUpdateRequest ratingUpdateRequest) throws Exception {
        try {
            UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

            User user = userSession.getUser();

            Long ratingUser = utility.findUserIdByEmail(user.getEmailId());
            Long ratedUser = utility.findUserIdByEmail(ratingUpdateRequest.getRatedUserEmailId());

            Map<String, Object> map = new HashMap<>();

            map.put("value", ratingUpdateRequest.getRate());
            map.put("ratingUser", ratingUser);
            map.put("ratedUser", ratedUser);
            map.put("ratingComment", ratingUpdateRequest.getComment());

            String sql = "UPDATE rating SET value=:value, rating_comment=:ratingComment WHERE rated_user=:ratedUser AND rating_user= :ratingUser";

            int update = jdbcTemplate.update(sql, map);

            return update == 1;
        } catch (Exception e) {
            throw new Exception("Unable to uodate rating");
        }
    }

}
