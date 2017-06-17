
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryTransactionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QueryTransactionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Authorisation"/>
 *     &lt;enumeration value="Settlement"/>
 *     &lt;enumeration value="Refund"/>
 *     &lt;enumeration value="Payout"/>
 *     &lt;enumeration value="Purchase"/>
 *     &lt;enumeration value="All"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "QueryTransactionType")
@XmlEnum
public enum QueryTransactionType {

    @XmlEnumValue("Authorisation")
    AUTHORISATION("Authorisation"),
    @XmlEnumValue("Settlement")
    SETTLEMENT("Settlement"),
    @XmlEnumValue("Refund")
    REFUND("Refund"),
    @XmlEnumValue("Payout")
    PAYOUT("Payout"),
    @XmlEnumValue("Purchase")
    PURCHASE("Purchase"),
    @XmlEnumValue("All")
    ALL("All");
    private final String value;

    QueryTransactionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QueryTransactionType fromValue(String v) {
        for (QueryTransactionType c: QueryTransactionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
