package za.co.dladle.serviceutil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import za.co.dladle.paygate.PayHOST;
import za.co.dladle.paygate.PayHOSTService;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by prady on 6/24/2017.
 */
@Component
public class WebServiceClient {
    private PayHOST payHOST = null;
    @Value("${paygate.wsdl.url}")
    private String wsdlUrl;

    public PayHOST getPayHOST() {
        return payHOST;
    }

    @PostConstruct
    public void setPayHOST() throws MalformedURLException {
        PayHOSTService payHOSTService = new PayHOSTService(new URL(wsdlUrl));
        payHOST = payHOSTService.getPayHOSTSoap11();
    }
}
