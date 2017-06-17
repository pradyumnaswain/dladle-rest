
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatusNameType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StatusNameType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Error"/>
 *     &lt;enumeration value="Pending"/>
 *     &lt;enumeration value="Cancelled"/>
 *     &lt;enumeration value="Completed"/>
 *     &lt;enumeration value="RiskRejected"/>
 *     &lt;enumeration value="ValidationError"/>
 *     &lt;enumeration value="WebRedirectRequired"/>
 *     &lt;enumeration value="ThreeDSecureRedirectRequired"/>
 *     &lt;enumeration value="VaultFailure"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatusNameType")
@XmlEnum
public enum StatusNameType {

    @XmlEnumValue("Error")
    ERROR("Error"),
    @XmlEnumValue("Pending")
    PENDING("Pending"),
    @XmlEnumValue("Cancelled")
    CANCELLED("Cancelled"),
    @XmlEnumValue("Completed")
    COMPLETED("Completed"),
    @XmlEnumValue("RiskRejected")
    RISK_REJECTED("RiskRejected"),
    @XmlEnumValue("ValidationError")
    VALIDATION_ERROR("ValidationError"),
    @XmlEnumValue("WebRedirectRequired")
    WEB_REDIRECT_REQUIRED("WebRedirectRequired"),
    @XmlEnumValue("ThreeDSecureRedirectRequired")
    THREE_D_SECURE_REDIRECT_REQUIRED("ThreeDSecureRedirectRequired"),
    @XmlEnumValue("VaultFailure")
    VAULT_FAILURE("VaultFailure");
    private final String value;

    StatusNameType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatusNameType fromValue(String v) {
        for (StatusNameType c: StatusNameType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
