
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PayGateAccountType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="PayGateAccountType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PayGateId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayGateAccountType", propOrder = {
        "payGateId",
        "password"
})
public class PayGateAccountType {

    @XmlElement(name = "PayGateId", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String payGateId;
    @XmlElement(name = "Password", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String password;

    /**
     * Gets the value of the payGateId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPayGateId() {
        return payGateId;
    }

    /**
     * Sets the value of the payGateId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayGateId(String value) {
        this.payGateId = value;
    }

    /**
     * Gets the value of the password property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPassword(String value) {
        this.password = value;
    }

}
