package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.model.PaymentCard;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

    public void addCard(PaymentCard paymentCard) throws Exception {

//        SingleVaultResponse singleVaultResponse = payGateService.singleVault(paymentCard);
//        if (singleVaultResponse.getCardVaultResponse() != null) {
//            String vaultId = singleVaultResponse.getCardVaultResponse().getStatus().getVaultId();

//            if (vaultId != null) {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

//        String cardNumber = paymentCard.getCardNumber().substring(12);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());
        map.put("userId", userId);
        map.put("nameOnCard", paymentCard.getNameOnCard());
        map.put("cardNumber", paymentCard.getCardNumber());
        map.put("cardType", paymentCard.getCardType());
        map.put("expiryDate", paymentCard.getExpiryDate());
//                map.put("vaultId", vaultId);
        map.put("time", LocalDateTime.now());

        String sql = "INSERT INTO payment_card(user_id, card_update_time, name_on_card, card_number, card_type,expiry_date) " +
                "VALUES (:userId,:time,:nameOnCard,:cardNumber,:cardType,:expiryDate) ON CONFLICT DO NOTHING ";
        this.jdbcTemplate.update(sql, map);
//            } else throw new Exception("Unable to persist card data");
//        }
    }

    public void updateCard(PaymentCard paymentCard) throws Exception {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

//        deleteCard(userSession.getUser().getEmailId());

//        SingleVaultResponse singleVaultResponse = payGateService.singleVault(paymentCard);
//
//        String vaultId = singleVaultResponse.getCardVaultResponse().getStatus().getVaultId();
//
//        String cardNumber = paymentCard.getCardNumber().substring(12);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());
        map.put("userId", userId);
        map.put("nameOnCard", paymentCard.getNameOnCard());
        map.put("cardNumber", paymentCard.getCardNumber());
        map.put("cardType", paymentCard.getCardType());
        map.put("expiryDate", paymentCard.getExpiryDate());
        map.put("time", LocalDateTime.now());
//        map.put("vaultId", vaultId);

        String sql = "UPDATE payment_card SET name_on_card=:nameOnCard,card_update_time=:time,expiry_date=:expiryDate WHERE user_id=:userId AND card_number=:cardNumber";
        this.jdbcTemplate.update(sql, map);
    }

    public void deleteCard(String cardNumber) throws Exception {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());

        map.put("userId", userId);
        map.put("cardNumber", cardNumber);

//        String sql1 = "SELECT vaultid FROM payment_card WHERE user_id=:userId";
//
//        String vaultId = this.jdbcTemplate.queryForObject(sql1, map, String.class);
//
//        SingleVaultResponse singleVaultResponse = payGateService.deleteVault(vaultId);
//
//        StatusNameType statusName = singleVaultResponse.getDeleteVaultResponse().getStatus().getStatusName();
//
//        if (statusName.equals(StatusNameType.COMPLETED)) {
        String sql = "DELETE FROM payment_card WHERE user_id=:userId AND card_number=:cardNumber";
        this.jdbcTemplate.update(sql, map);
//        } else {
//            throw new Exception("Unable to delete card");
//        }
    }

//    private void deleteCard(String emailId) throws Exception {
//        Map<String, Object> map = new HashMap<>();
//
//        Long userId = userUtility.findUserIdByEmail(emailId);
//        map.put("userId", userId);
//
//        String sql1 = "SELECT vaultid FROM payment_card WHERE user_id=:userId";
//
//        String vaultId = this.jdbcTemplate.queryForObject(sql1, map, String.class);
//
//        payGateService.deleteVault(vaultId);
//    }

    public List<PaymentCard> viewCard() throws Exception {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());

        map.put("userId", userId);

        String sql = "SELECT * FROM payment_card WHERE user_id=:userId";

        try {
            return this.jdbcTemplate.query(sql, map, (rs, rowNum) -> new PaymentCard(
                    rs.getLong("id"),
                    rs.getString("name_on_card"),
                    rs.getString("card_number"),
                    rs.getString("expiry_date"),
                    rs.getString("card_type")));
        } catch (Exception e) {
            throw new Exception("No Card available for user");
        }
    }
}
