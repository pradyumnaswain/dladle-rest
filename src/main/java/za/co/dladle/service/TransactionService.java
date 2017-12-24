package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.Transaction;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.mapper.OperationTypeMapper;
import za.co.dladle.mapper.TransactionStatusMapper;
import za.co.dladle.mapper.TransactionTypeMapper;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private UserUtility userUtility;

    public List<Transaction> listTransactions() throws UserNotFoundException {
        UserSession userSession = applicationContext.getBean(UserSession.class);

        String sql = "SELECT * FROM transaction INNER JOIN payment_card ON transaction.card_id = payment_card.id" +
                " WHERE transaction.user_id=:userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userUtility.findUserIdByEmail(userSession.getUser().getEmailId()));

        return this.namedParameterJdbcTemplate.query(sql, map, (rs, rowNum) -> new Transaction(rs.getString("reference_no"),
                rs.getDouble("amount"),
                TransactionTypeMapper.getTransactionType(rs.getInt("transaction_type")),
                OperationTypeMapper.getOperationType(rs.getInt("operation_type")),
                TransactionStatusMapper.getTransactionStatus(rs.getInt("transaction_status")),
                rs.getString("transaction_time"), rs.getString("card_number")));
    }
}
