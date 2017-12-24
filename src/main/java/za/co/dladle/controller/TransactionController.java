package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
import za.co.dladle.entity.Transaction;
import za.co.dladle.service.TransactionService;
import za.co.dladle.serviceutil.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/22/2017.
 */
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    //------------------------------------------------------------------------------------------------------------------
    //View Transactions
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.TRANSACTIONS_VIEW, method = RequestMethod.GET)
    public Map<String, Object> viewTransaction() throws IOException {
        try {
            List<Transaction> paymentCard = transactionService.listTransactions();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, paymentCard, DladleConstants.TRANSACTION_VIEW);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
