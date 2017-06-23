
package za.co.dladle.paygate;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 */
@WebServiceClient(name = "PayHOSTService", targetNamespace = "http://www.paygate.co.za/PayHOST", wsdlLocation = "file:/C:/Projects/company/dladle-rest/src/main/resources/wsdl/paygate.wsdl")
public class PayHOSTService
        extends Service {

    private final static URL PAYHOSTSERVICE_WSDL_LOCATION;
    private final static WebServiceException PAYHOSTSERVICE_EXCEPTION;
    private final static QName PAYHOSTSERVICE_QNAME = new QName("http://www.paygate.co.za/PayHOST", "PayHOSTService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Projects/company/dladle-rest/src/main/resources/wsdl/paygate.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PAYHOSTSERVICE_WSDL_LOCATION = url;
        PAYHOSTSERVICE_EXCEPTION = e;
    }

    public PayHOSTService() {
        super(__getWsdlLocation(), PAYHOSTSERVICE_QNAME);
    }

    public PayHOSTService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PAYHOSTSERVICE_QNAME, features);
    }

    public PayHOSTService(URL wsdlLocation) {
        super(wsdlLocation, PAYHOSTSERVICE_QNAME);
    }

    public PayHOSTService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PAYHOSTSERVICE_QNAME, features);
    }

    public PayHOSTService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PayHOSTService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * @return returns PayHOST
     */
    @WebEndpoint(name = "PayHOSTSoap11")
    public za.co.dladle.paygate.PayHOST getPayHOSTSoap11() {
        return super.getPort(new QName("http://www.paygate.co.za/PayHOST", "PayHOSTSoap11"), za.co.dladle.paygate.PayHOST.class);
    }

    /**
     * @param features A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns PayHOST
     */
    @WebEndpoint(name = "PayHOSTSoap11")
    public za.co.dladle.paygate.PayHOST getPayHOSTSoap11(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.paygate.co.za/PayHOST", "PayHOSTSoap11"), za.co.dladle.paygate.PayHOST.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PAYHOSTSERVICE_EXCEPTION != null) {
            throw PAYHOSTSERVICE_EXCEPTION;
        }
        return PAYHOSTSERVICE_WSDL_LOCATION;
    }

}
