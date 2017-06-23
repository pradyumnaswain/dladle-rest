package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.PaymentCard;
import za.co.dladle.session.UserSession;
import za.co.dladle.util.UserUtility;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 6/22/2017.
 */
@Service
public class WalletService {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserUtility userUtility;

    public void addCard(PaymentCard paymentCard) throws UserNotFoundException {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());
        map.put("userId", userId);
        map.put("nameOnCard", paymentCard.getNameOnCard());
        map.put("cardNumber", paymentCard.getCardNumber());
        map.put("expiryDate", paymentCard.getExpiryDate());
        map.put("cvvNumber", paymentCard.getCvvNumber());
        map.put("cardType", paymentCard.getCardType());
        map.put("time", LocalDateTime.now());

        String sql = "INSERT INTO payment_card(user_id, card_update_time, name_on_card, card_number, expiry_date, cvv_number, card_type) " +
                "VALUES (:userId,:time,:nameOnCard,:cardNumber,:expiryDate,:cvvNumber,:cardType) ON CONFLICT DO NOTHING ";
        this.jdbcTemplate.update(sql, map);
    }

    public void updateCard(PaymentCard paymentCard) throws UserNotFoundException {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());
        map.put("userId", userId);
        map.put("nameOnCard", paymentCard.getNameOnCard());
        map.put("cardNumber", paymentCard.getCardNumber());
        map.put("expiryDate", paymentCard.getExpiryDate());
        map.put("cvvNumber", paymentCard.getCvvNumber());
        map.put("cardType", paymentCard.getCardType());
        map.put("time", LocalDateTime.now());

        String sql = "UPDATE payment_card SET name_on_card=:nameOnCard,card_number=:cardNumber,expiry_date=:expiryDate,cvv_number=:cvvNumber,card_type=:cardType,card_update_time=:time WHERE user_id=:userId";
        this.jdbcTemplate.update(sql, map);
    }

    public void deleteCard() throws UserNotFoundException {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());
        map.put("userId", userId);

        String sql = "DELETE FROM payment_card WHERE user_id=:userId";
        this.jdbcTemplate.update(sql, map);
    }

    public PaymentCard viewCard() throws Exception {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());
        map.put("userId", userId);

        String sql = "SELECT * FROM payment_card WHERE user_id=:userId";

        try {
            return this.jdbcTemplate.queryForObject(sql, map, (rs, rowNum) -> new PaymentCard(rs.getString("name_on_card"),
                    rs.getString("card_number"),
                    rs.getString("expiry_date"),
                    rs.getString("cvv_number"),
                    rs.getString("card_type")));
        } catch (Exception e) {
            throw new Exception("No Card available for user");
        }
    }
}
