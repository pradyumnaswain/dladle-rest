package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.dladle.model.PaymentCard;
import za.co.dladle.paygate.CardVaultRequestType;
import za.co.dladle.paygate.DeleteVaultRequestType;
import za.co.dladle.paygate.SingleVaultRequest;
import za.co.dladle.paygate.SingleVaultResponse;
import za.co.dladle.util.PayGateUtil;
import za.co.dladle.util.WebServiceClient;

/**
 * Created by prady on 6/24/2017.
 */
@Service
public class PayGateService {

    @Autowired
    private WebServiceClient webServiceClient;

    @Autowired
    private PayGateUtil payGateUtil;

    SingleVaultResponse singleVault(PaymentCard paymentCard) {
        SingleVaultRequest singleVaultRequest = new SingleVaultRequest();

        CardVaultRequestType cardVaultRequestType = new CardVaultRequestType();

        cardVaultRequestType.setAccount(payGateUtil.gateAccountType());

        cardVaultRequestType.setCardNumber(paymentCard.getCardNumber());

        cardVaultRequestType.setCardExpiryDate(paymentCard.getExpiryDate());

        singleVaultRequest.setCardVaultRequest(cardVaultRequestType);

        return webServiceClient.getPayHOST().singleVault(singleVaultRequest);
    }

    SingleVaultResponse deleteVault(String vaultId) {
        SingleVaultRequest singleVaultRequest = new SingleVaultRequest();

        DeleteVaultRequestType deleteVaultRequestType = new DeleteVaultRequestType();

        deleteVaultRequestType.setAccount(payGateUtil.gateAccountType());

        deleteVaultRequestType.setVaultId(vaultId);

        singleVaultRequest.setDeleteVaultRequest(deleteVaultRequestType);

        return webServiceClient.getPayHOST().singleVault(singleVaultRequest);
    }
}
