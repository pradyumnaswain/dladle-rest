
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaultKeyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VaultKeyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="cardNumber"/>
 *     &lt;enumeration value="expDate"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VaultKeyType")
@XmlEnum
public enum VaultKeyType {

    @XmlEnumValue("cardNumber")
    CARD_NUMBER("cardNumber"),
    @XmlEnumValue("expDate")
    EXP_DATE("expDate");
    private final String value;

    VaultKeyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VaultKeyType fromValue(String v) {
        for (VaultKeyType c: VaultKeyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
