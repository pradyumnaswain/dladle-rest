
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="NotDone"/>
 *     &lt;enumeration value="Approved"/>
 *     &lt;enumeration value="Declined"/>
 *     &lt;enumeration value="Paid"/>
 *     &lt;enumeration value="Refunded"/>
 *     &lt;enumeration value="ReceivedByPaygate"/>
 *     &lt;enumeration value="RepliedToClient"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionCodeType")
@XmlEnum
public enum TransactionCodeType {

    @XmlEnumValue("NotDone")
    NOT_DONE("NotDone"),
    @XmlEnumValue("Approved")
    APPROVED("Approved"),
    @XmlEnumValue("Declined")
    DECLINED("Declined"),
    @XmlEnumValue("Paid")
    PAID("Paid"),
    @XmlEnumValue("Refunded")
    REFUNDED("Refunded"),
    @XmlEnumValue("ReceivedByPaygate")
    RECEIVED_BY_PAYGATE("ReceivedByPaygate"),
    @XmlEnumValue("RepliedToClient")
    REPLIED_TO_CLIENT("RepliedToClient");
    private final String value;

    TransactionCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransactionCodeType fromValue(String v) {
        for (TransactionCodeType c: TransactionCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
