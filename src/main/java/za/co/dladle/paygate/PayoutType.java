
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayoutType.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PayoutType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Eft"/>
 *     &lt;enumeration value="Salary"/>
 *     &lt;enumeration value="PayAndClear"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PayoutType")
@XmlEnum
public enum PayoutType {

    @XmlEnumValue("Eft")
    EFT("Eft"),
    @XmlEnumValue("Salary")
    SALARY("Salary"),
    @XmlEnumValue("PayAndClear")
    PAY_AND_CLEAR("PayAndClear");
    private final String value;

    PayoutType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PayoutType fromValue(String v) {
        for (PayoutType c : PayoutType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
