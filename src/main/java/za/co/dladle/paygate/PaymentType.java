
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PaymentType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="PaymentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Method" type="{http://www.paygate.co.za/PayHOST}PaymentMethodType"/>
 *         &lt;element name="Detail" type="{http://www.paygate.co.za/PayHOST}PaymentDetailType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentType", propOrder = {
        "method",
        "detail"
})
public class PaymentType {

    @XmlElement(name = "Method", required = true)
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.PaymentMethodType method;
    @XmlElement(name = "Detail")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String detail;

    /**
     * Gets the value of the method property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.PaymentMethodType }
     */
    public za.co.dladle.paygate.PaymentMethodType getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.PaymentMethodType }
     */
    public void setMethod(za.co.dladle.paygate.PaymentMethodType value) {
        this.method = value;
    }

    /**
     * Gets the value of the detail property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDetail(String value) {
        this.detail = value;
    }

}
