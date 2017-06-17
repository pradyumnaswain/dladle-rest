
package za.co.dladle.paygate;

import za.co.dladle.paygate.*;
import za.co.dladle.paygate.KeyValueType;
import za.co.dladle.paygate.OrderType;
import za.co.dladle.paygate.PaymentType;
import za.co.dladle.paygate.PersonType;
import za.co.dladle.paygate.RedirectRequestType;
import za.co.dladle.paygate.RiskType;
import za.co.dladle.paygate.ThreeDSecureType;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for WebPaymentRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WebPaymentRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Account" type="{http://www.paygate.co.za/PayHOST}PayGateAccountType"/>
 *         &lt;element name="Customer" type="{http://www.paygate.co.za/PayHOST}PersonType"/>
 *         &lt;element name="PaymentType" type="{http://www.paygate.co.za/PayHOST}PaymentType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Redirect" type="{http://www.paygate.co.za/PayHOST}RedirectRequestType"/>
 *         &lt;element name="Order" type="{http://www.paygate.co.za/PayHOST}OrderType"/>
 *         &lt;element name="ThreeDSecure" type="{http://www.paygate.co.za/PayHOST}ThreeDSecureType" minOccurs="0"/>
 *         &lt;element name="Risk" type="{http://www.paygate.co.za/PayHOST}RiskType" minOccurs="0"/>
 *         &lt;element name="UserDefinedFields" type="{http://www.paygate.co.za/PayHOST}KeyValueType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="BillingDescriptor" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WebPaymentRequestType", propOrder = {
    "account",
    "customer",
    "paymentType",
    "redirect",
    "order",
    "threeDSecure",
    "risk",
    "userDefinedFields",
    "billingDescriptor"
})
public class WebPaymentRequestType {

    @XmlElement(name = "Account", required = true)
    protected za.co.dladle.paygate.PayGateAccountType account;
    @XmlElement(name = "Customer", required = true)
    protected za.co.dladle.paygate.PersonType customer;
    @XmlElement(name = "PaymentType")
    protected List<za.co.dladle.paygate.PaymentType> paymentType;
    @XmlElement(name = "Redirect", required = true)
    protected za.co.dladle.paygate.RedirectRequestType redirect;
    @XmlElement(name = "Order", required = true)
    protected za.co.dladle.paygate.OrderType order;
    @XmlElement(name = "ThreeDSecure")
    protected za.co.dladle.paygate.ThreeDSecureType threeDSecure;
    @XmlElement(name = "Risk")
    protected za.co.dladle.paygate.RiskType risk;
    @XmlElement(name = "UserDefinedFields")
    protected List<za.co.dladle.paygate.KeyValueType> userDefinedFields;
    @XmlElement(name = "BillingDescriptor")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String billingDescriptor;

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.PayGateAccountType }
     *     
     */
    public za.co.dladle.paygate.PayGateAccountType getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.PayGateAccountType }
     *     
     */
    public void setAccount(za.co.dladle.paygate.PayGateAccountType value) {
        this.account = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.PersonType }
     *     
     */
    public za.co.dladle.paygate.PersonType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.PersonType }
     *     
     */
    public void setCustomer(PersonType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link za.co.dladle.paygate.PaymentType }
     * 
     * 
     */
    public List<za.co.dladle.paygate.PaymentType> getPaymentType() {
        if (paymentType == null) {
            paymentType = new ArrayList<PaymentType>();
        }
        return this.paymentType;
    }

    /**
     * Gets the value of the redirect property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.RedirectRequestType }
     *     
     */
    public za.co.dladle.paygate.RedirectRequestType getRedirect() {
        return redirect;
    }

    /**
     * Sets the value of the redirect property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.RedirectRequestType }
     *     
     */
    public void setRedirect(RedirectRequestType value) {
        this.redirect = value;
    }

    /**
     * Gets the value of the order property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.OrderType }
     *     
     */
    public za.co.dladle.paygate.OrderType getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.OrderType }
     *     
     */
    public void setOrder(OrderType value) {
        this.order = value;
    }

    /**
     * Gets the value of the threeDSecure property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.ThreeDSecureType }
     *     
     */
    public za.co.dladle.paygate.ThreeDSecureType getThreeDSecure() {
        return threeDSecure;
    }

    /**
     * Sets the value of the threeDSecure property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.ThreeDSecureType }
     *     
     */
    public void setThreeDSecure(ThreeDSecureType value) {
        this.threeDSecure = value;
    }

    /**
     * Gets the value of the risk property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.RiskType }
     *     
     */
    public za.co.dladle.paygate.RiskType getRisk() {
        return risk;
    }

    /**
     * Sets the value of the risk property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.RiskType }
     *     
     */
    public void setRisk(RiskType value) {
        this.risk = value;
    }

    /**
     * Gets the value of the userDefinedFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userDefinedFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserDefinedFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link za.co.dladle.paygate.KeyValueType }
     * 
     * 
     */
    public List<za.co.dladle.paygate.KeyValueType> getUserDefinedFields() {
        if (userDefinedFields == null) {
            userDefinedFields = new ArrayList<KeyValueType>();
        }
        return this.userDefinedFields;
    }

    /**
     * Gets the value of the billingDescriptor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingDescriptor() {
        return billingDescriptor;
    }

    /**
     * Sets the value of the billingDescriptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingDescriptor(String value) {
        this.billingDescriptor = value;
    }

}
