package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.dladle.model.PaymentCard;
import za.co.dladle.model.User;
import za.co.dladle.paygate.*;
import za.co.dladle.serviceutil.PayGateUtil;
import za.co.dladle.serviceutil.WebServiceClient;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by prady on 6/24/2017.
 */
@Service
public class PayGateService {

    @Autowired
    private WebServiceClient webServiceClient;

    @Autowired
    private PayGateUtil payGateUtil;

    //------------------------------------------------------------------------------------------------------------------
    //Save Card
    //------------------------------------------------------------------------------------------------------------------
    SingleVaultResponse singleVault(PaymentCard paymentCard) {
        SingleVaultRequest singleVaultRequest = new SingleVaultRequest();

        CardVaultRequestType cardVaultRequestType = new CardVaultRequestType();

        cardVaultRequestType.setAccount(payGateUtil.gateAccountType());

        cardVaultRequestType.setCardNumber(paymentCard.getCardNumber());

        cardVaultRequestType.setCardExpiryDate(paymentCard.getExpiryDate());

        singleVaultRequest.setCardVaultRequest(cardVaultRequestType);

        return webServiceClient.getPayHOST().singleVault(singleVaultRequest);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Delete Card
    //------------------------------------------------------------------------------------------------------------------
    SingleVaultResponse deleteVault(String vaultId) {
        SingleVaultRequest singleVaultRequest = new SingleVaultRequest();

        DeleteVaultRequestType deleteVaultRequestType = new DeleteVaultRequestType();

        deleteVaultRequestType.setAccount(payGateUtil.gateAccountType());

        deleteVaultRequestType.setVaultId(vaultId);

        singleVaultRequest.setDeleteVaultRequest(deleteVaultRequestType);

        return webServiceClient.getPayHOST().singleVault(singleVaultRequest);
    }
    //------------------------------------------------------------------------------------------------------------------
    //Card request Using Token
    //------------------------------------------------------------------------------------------------------------------

    CardPaymentResponseType paymentRequest(User user, String cardNumber, String expiryDate, String cvvNumber, String merchantId, Integer amount, String notifUrl, String returnUrl) throws DatatypeConfigurationException {
        SinglePaymentRequest singlePaymentRequest = new SinglePaymentRequest();

        CardPaymentRequestType cardPaymentRequestType = new CardPaymentRequestType();

        cardPaymentRequestType.setAccount(payGateUtil.gateAccountType());

        cardPaymentRequestType.setCustomer(getPersonType(user));

        cardPaymentRequestType.setCardNumber(cardNumber);
        cardPaymentRequestType.setCardExpiryDate(expiryDate);
        cardPaymentRequestType.setCVV(cvvNumber);

//        cardPaymentRequestType.setBudgetPeriod();
        cardPaymentRequestType.setRedirect(getRedirect(notifUrl, returnUrl));

        cardPaymentRequestType.setOrder(getOrderType(merchantId, amount));

        singlePaymentRequest.setCardPaymentRequest(cardPaymentRequestType);

        SinglePaymentResponse singlePaymentResponse = webServiceClient.getPayHOST().singlePayment(singlePaymentRequest);

        return singlePaymentResponse.getCardPaymentResponse();
    }

    private PersonType getPersonType(User user) {
        PersonType personType = new PersonType();

        personType.setFirstName(user.getFirstName());

        personType.setLastName(user.getLastName());

        if (user.getMobileNumber() != null) {
            personType.getMobile().add(user.getMobileNumber());
        }

        personType.getEmail().add(user.getEmailId());

        if (user.getIdNumber() != null) {
            personType.setIdNumber(user.getIdNumber());
        }
        return personType;
    }

    private OrderType getOrderType(String merchantId, Integer amount) throws DatatypeConfigurationException {
        OrderType orderType = new OrderType();
        orderType.setMerchantOrderId(merchantId);
        orderType.setCurrency(CurrencyType.ZAR);
        orderType.setAmount(amount);
//        orderType.setDiscount();
        GregorianCalendar c = new GregorianCalendar();
        Date date = new Date();
        c.setTime(date);
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        orderType.setTransactionDate(xmlGregorianCalendar);
        return orderType;
    }

    private RedirectRequestType getRedirect(String notifUrl, String returnUrl) {
        RedirectRequestType redirectRequestType = new RedirectRequestType();

        redirectRequestType.setNotifyUrl(notifUrl);

        redirectRequestType.setReturnUrl(returnUrl);

        return redirectRequestType;
    }
}
