package za.co.dladle.serviceutil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import za.co.dladle.paygate.PayGateAccountType;

/**
 * Created by prady on 6/24/2017.
 */
@Component
public class PayGateUtil {
    @Value("${paygate.paygateId}")
    private String paygateId;

    @Value("${paygate.password}")
    private String password;

    public PayGateAccountType gateAccountType() {
        PayGateAccountType payGateAccountType = new PayGateAccountType();
        payGateAccountType.setPayGateId(paygateId);
        payGateAccountType.setPassword(password);

        return payGateAccountType;
    }
}
