
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentMethodType.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentMethodType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="CC"/>
 *     &lt;enumeration value="DC"/>
 *     &lt;enumeration value="EW"/>
 *     &lt;enumeration value="BT"/>
 *     &lt;enumeration value="CV"/>
 *     &lt;enumeration value="PC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PaymentMethodType")
@XmlEnum
public enum PaymentMethodType {


    /**
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;p xmlns:sch="http://www.paygate.co.za/PayHOST" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://www.paygate.co.za/PayHOST" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xspg="http://www.paygate.co.za/PayHOST"&gt;Credit Card&lt;/p&gt;
     * </pre>
     */
    CC,

    /**
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;p xmlns:sch="http://www.paygate.co.za/PayHOST" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://www.paygate.co.za/PayHOST" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xspg="http://www.paygate.co.za/PayHOST"&gt;Debit Card&lt;/p&gt;
     * </pre>
     */
    DC,

    /**
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;p xmlns:sch="http://www.paygate.co.za/PayHOST" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://www.paygate.co.za/PayHOST" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xspg="http://www.paygate.co.za/PayHOST"&gt;eWallet&lt;/p&gt;
     * </pre>
     */
    EW,

    /**
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;p xmlns:sch="http://www.paygate.co.za/PayHOST" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://www.paygate.co.za/PayHOST" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xspg="http://www.paygate.co.za/PayHOST"&gt;Bank Transfer&lt;/p&gt;
     * </pre>
     */
    BT,

    /**
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;p xmlns:sch="http://www.paygate.co.za/PayHOST" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://www.paygate.co.za/PayHOST" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xspg="http://www.paygate.co.za/PayHOST"&gt;Cash Voucher&lt;/p&gt;
     * </pre>
     */
    CV,

    /**
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;p xmlns:sch="http://www.paygate.co.za/PayHOST" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://www.paygate.co.za/PayHOST" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xspg="http://www.paygate.co.za/PayHOST"&gt;Pre-Paid Card&lt;/p&gt;
     * </pre>
     */
    PC;

    public String value() {
        return name();
    }

    public static PaymentMethodType fromValue(String v) {
        return valueOf(v);
    }

}
