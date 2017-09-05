package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
import za.co.dladle.entity.PaymentRequest;
import za.co.dladle.entity.PaymentResponse;
import za.co.dladle.model.PaymentCard;
import za.co.dladle.service.PaymentService;
import za.co.dladle.serviceutil.ResponseUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by prady on 9/5/2017.
 */
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    //------------------------------------------------------------------------------------------------------------------
    //Add Card
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PAYMENT_PAY, method = RequestMethod.POST)
    public Map<String, Object> addCard(@RequestBody PaymentRequest paymentRequest) throws IOException {
        try {
            PaymentResponse paymentResponse = paymentService.pay(paymentRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, paymentResponse, DladleConstants.PAYMENT_PAY);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

}
