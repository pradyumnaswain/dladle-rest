
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for TokenPaymentRequestType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="TokenPaymentRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Account" type="{http://www.paygate.co.za/PayHOST}PayGateAccountType"/>
 *         &lt;element name="Customer" type="{http://www.paygate.co.za/PayHOST}PersonType" minOccurs="0"/>
 *         &lt;element name="Token" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="TokenDetail" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="Vault" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Redirect" type="{http://www.paygate.co.za/PayHOST}RedirectRequestType" minOccurs="0"/>
 *         &lt;element name="Order" type="{http://www.paygate.co.za/PayHOST}OrderType"/>
 *         &lt;element name="Risk" type="{http://www.paygate.co.za/PayHOST}RiskType" minOccurs="0"/>
 *         &lt;element name="UserDefinedFields" type="{http://www.paygate.co.za/PayHOST}KeyValueType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="BillingDescriptor" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TokenPaymentRequestType", propOrder = {
        "account",
        "customer",
        "token",
        "tokenDetail",
        "vault",
        "redirect",
        "order",
        "risk",
        "userDefinedFields",
        "billingDescriptor"
})
public class TokenPaymentRequestType {

    @XmlElement(name = "Account", required = true)
    protected za.co.dladle.paygate.PayGateAccountType account;
    @XmlElement(name = "Customer")
    protected za.co.dladle.paygate.PersonType customer;
    @XmlElement(name = "Token", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String token;
    @XmlElement(name = "TokenDetail", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String tokenDetail;
    @XmlElement(name = "Vault", defaultValue = "false")
    protected Boolean vault;
    @XmlElement(name = "Redirect")
    protected za.co.dladle.paygate.RedirectRequestType redirect;
    @XmlElement(name = "Order", required = true)
    protected za.co.dladle.paygate.OrderType order;
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
     * @return possible object is
     * {@link za.co.dladle.paygate.PayGateAccountType }
     */
    public za.co.dladle.paygate.PayGateAccountType getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.PayGateAccountType }
     */
    public void setAccount(za.co.dladle.paygate.PayGateAccountType value) {
        this.account = value;
    }

    /**
     * Gets the value of the customer property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.PersonType }
     */
    public za.co.dladle.paygate.PersonType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.PersonType }
     */
    public void setCustomer(PersonType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the token property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the tokenDetail property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTokenDetail() {
        return tokenDetail;
    }

    /**
     * Sets the value of the tokenDetail property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTokenDetail(String value) {
        this.tokenDetail = value;
    }

    /**
     * Gets the value of the vault property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isVault() {
        return vault;
    }

    /**
     * Sets the value of the vault property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setVault(Boolean value) {
        this.vault = value;
    }

    /**
     * Gets the value of the redirect property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.RedirectRequestType }
     */
    public za.co.dladle.paygate.RedirectRequestType getRedirect() {
        return redirect;
    }

    /**
     * Sets the value of the redirect property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.RedirectRequestType }
     */
    public void setRedirect(RedirectRequestType value) {
        this.redirect = value;
    }

    /**
     * Gets the value of the order property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.OrderType }
     */
    public za.co.dladle.paygate.OrderType getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.OrderType }
     */
    public void setOrder(OrderType value) {
        this.order = value;
    }

    /**
     * Gets the value of the risk property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.RiskType }
     */
    public za.co.dladle.paygate.RiskType getRisk() {
        return risk;
    }

    /**
     * Sets the value of the risk property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.RiskType }
     */
    public void setRisk(RiskType value) {
        this.risk = value;
    }

    /**
     * Gets the value of the userDefinedFields property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userDefinedFields property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserDefinedFields().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link za.co.dladle.paygate.KeyValueType }
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
     * @return possible object is
     * {@link String }
     */
    public String getBillingDescriptor() {
        return billingDescriptor;
    }

    /**
     * Sets the value of the billingDescriptor property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBillingDescriptor(String value) {
        this.billingDescriptor = value;
    }

}
