
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BankAccountType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BankAccountType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Current"/>
 *     &lt;enumeration value="Savings"/>
 *     &lt;enumeration value="Transmission"/>
 *     &lt;enumeration value="Bond"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BankAccountType")
@XmlEnum
public enum BankAccountType {

    @XmlEnumValue("Current")
    CURRENT("Current"),
    @XmlEnumValue("Savings")
    SAVINGS("Savings"),
    @XmlEnumValue("Transmission")
    TRANSMISSION("Transmission"),
    @XmlEnumValue("Bond")
    BOND("Bond");
    private final String value;

    BankAccountType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BankAccountType fromValue(String v) {
        for (BankAccountType c: BankAccountType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
