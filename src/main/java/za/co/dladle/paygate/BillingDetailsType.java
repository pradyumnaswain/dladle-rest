
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingDetailsType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="BillingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Customer" type="{http://www.paygate.co.za/PayHOST}PersonType"/>
 *         &lt;element name="Address" type="{http://www.paygate.co.za/PayHOST}AddressType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingDetailsType", propOrder = {
        "customer",
        "address"
})
public class BillingDetailsType {

    @XmlElement(name = "Customer", required = true)
    protected PersonType customer;
    @XmlElement(name = "Address", required = true)
    protected za.co.dladle.paygate.AddressType address;

    /**
     * Gets the value of the customer property.
     *
     * @return possible object is
     * {@link PersonType }
     */
    public PersonType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     *
     * @param value allowed object is
     *              {@link PersonType }
     */
    public void setCustomer(PersonType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the address property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.AddressType }
     */
    public za.co.dladle.paygate.AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.AddressType }
     */
    public void setAddress(za.co.dladle.paygate.AddressType value) {
        this.address = value;
    }

}
