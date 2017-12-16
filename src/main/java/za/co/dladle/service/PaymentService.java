package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.PaymentRequest;
import za.co.dladle.entity.PaymentResponse;
import za.co.dladle.entity.UrlParams;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.mapper.OperationTypeMapper;
import za.co.dladle.paygate.CardPaymentResponseType;
import za.co.dladle.paygate.KeyValueType;
import za.co.dladle.paygate.RedirectResponseType;
import za.co.dladle.paygate.StatusNameType;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.viewmodel.PaymentCard;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prady on 9/5/2017.
 */
@Service
public class PaymentService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserUtility userUtility;

    @Autowired
    private PayGateService payGateService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public PaymentResponse pay(PaymentRequest paymentRequest) throws UserNotFoundException, DatatypeConfigurationException {

        PaymentResponse paymentResponse = new PaymentResponse();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());

        MapSqlParameterSource map = new MapSqlParameterSource().addValue("userId", userId)
                .addValue("userId", userId)
                .addValue("cardId", paymentRequest.getCardId())
                .addValue("amount", paymentRequest.getAmount())
                .addValue("transactionTime", LocalDateTime.now())
                .addValue("operationType", OperationTypeMapper.getOperationType(paymentRequest.getOperationType()));

        String sql = "SELECT expiry_date,card_number FROM payment_card WHERE user_id=:userId AND id=:cardId";

        PaymentCard paymentCard = this.jdbcTemplate.queryForObject(sql, map, (rs, rowNum) -> new PaymentCard(rs.getString("card_number"), rs.getString("expiry_date")));

        String sqlInsert = "INSERT INTO transaction (user_id, amount, operation_type,card_id,transaction_time) VALUES (:userId,:amount,:operationType,:cardId,:transactionTime)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(sqlInsert, map, keyHolder, new String[]{"id"});

        CardPaymentResponseType cardPaymentResponseType = payGateService.paymentRequest(userSession.getUser(), paymentCard.getCardNumber(),
                paymentCard.getExpiryDate(), paymentRequest.getCvvNumber(), String.valueOf(keyHolder.getKey().longValue()), paymentRequest.getAmount(), "http://www.dladle.com", "http://www.dladle.com");

        StatusNameType statusName = cardPaymentResponseType.getStatus().getStatusName();

        paymentResponse.setStatusName(statusName.name());

        paymentResponse.setStatusDetails(cardPaymentResponseType.getStatus().getStatusDetail());

        String sqlUpdate = "UPDATE transaction SET transaction_status=:status WHERE id=:transactionId";
        if (statusName.equals(StatusNameType.COMPLETED)) {
            map.addValue("transactionId", keyHolder.getKey().longValue()).addValue("status", 1);
            this.jdbcTemplate.update(sqlUpdate, map);
        } else if (statusName.equals(StatusNameType.PENDING)) {
            map.addValue("transactionId", keyHolder.getKey().longValue()).addValue("status", 3);
            this.jdbcTemplate.update(sqlUpdate, map);
        } else if (statusName.equals(StatusNameType.ERROR)) {
            map.addValue("transactionId", keyHolder.getKey().longValue()).addValue("status", 2);
            this.jdbcTemplate.update(sqlUpdate, map);
        }

        if (statusName.equals(StatusNameType.THREE_D_SECURE_REDIRECT_REQUIRED)) {
            RedirectResponseType redirect = cardPaymentResponseType.getRedirect();
            paymentResponse.setRedirectUrl(redirect.getRedirectUrl());
            List<UrlParams> urlParams = new ArrayList<>();
            for (KeyValueType keyValueType : redirect.getUrlParams()) {
                UrlParams urlParams1 = new UrlParams();
                urlParams1.setKey(keyValueType.getKey());
                urlParams1.setValue(keyValueType.getValue());
                urlParams.add(urlParams1);
            }
            paymentResponse.setUrlParams(urlParams);
        }
        return paymentResponse;
    }
}
