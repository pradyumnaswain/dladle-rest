package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.entity.ContactAddRequest;
import za.co.dladle.entity.DeleteContactRequest;
import za.co.dladle.model.PaymentCard;
import za.co.dladle.service.WalletService;
import za.co.dladle.util.ApiConstants;
import za.co.dladle.util.DladleConstants;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by prady on 6/22/2017.
 */
@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    //------------------------------------------------------------------------------------------------------------------
    //Add Card
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.CARD_ADD, method = RequestMethod.POST)
    public Map<String, Object> addCard(@RequestBody PaymentCard paymentCard) throws IOException {
        try {
            walletService.addCard(paymentCard);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.CARD_ADD);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update Card
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.CARD_UPDATE, method = RequestMethod.POST)
    public Map<String, Object> updateCard(@RequestBody PaymentCard paymentCard) throws IOException {
        try {
            walletService.updateCard(paymentCard);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.CARD_UPDATE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Delete Card
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.CARD_DELETE, method = RequestMethod.DELETE)
    public Map<String, Object> deleteCard() throws IOException {
        try {
            walletService.deleteCard();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.CARD_DELETE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Delete Card
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.CARD_VIEW, method = RequestMethod.GET)
    public Map<String, Object> viewCard() throws IOException {
        try {
            PaymentCard paymentCard = walletService.viewCard();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, paymentCard, DladleConstants.CARD_VIEW);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
