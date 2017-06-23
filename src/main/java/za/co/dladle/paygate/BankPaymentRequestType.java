
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for BankPaymentRequestType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="BankPaymentRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Account" type="{http://www.paygate.co.za/PayHOST}PayGateAccountType"/>
 *         &lt;element name="Customer" type="{http://www.paygate.co.za/PayHOST}PersonType"/>
 *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element name="AccountHolder" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *             &lt;element name="BankAccountType" type="{http://www.paygate.co.za/PayHOST}BankAccountType" minOccurs="0"/>
 *             &lt;element name="BankCode" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *             &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *             &lt;element name="BIC" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *             &lt;element name="IBAN" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *             &lt;element name="Country" type="{http://www.paygate.co.za/PayHOST}CountryType" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="VaultId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element name="Vault" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="BudgetPeriod" type="{http://www.paygate.co.za/PayHOST}BudgetPeriodType" minOccurs="0"/>
 *         &lt;element name="Redirect" type="{http://www.paygate.co.za/PayHOST}RedirectRequestType" minOccurs="0"/>
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
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankPaymentRequestType", propOrder = {
        "account",
        "customer",
        "accountNumber",
        "accountHolder",
        "bankAccountType",
        "bankCode",
        "bankName",
        "bic",
        "iban",
        "country",
        "vaultId",
        "vault",
        "budgetPeriod",
        "redirect",
        "order",
        "threeDSecure",
        "risk",
        "userDefinedFields",
        "billingDescriptor"
})
public class BankPaymentRequestType {

    @XmlElement(name = "Account", required = true)
    protected PayGateAccountType account;
    @XmlElement(name = "Customer", required = true)
    protected PersonType customer;
    @XmlElement(name = "AccountNumber", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String accountNumber;
    @XmlElement(name = "AccountHolder")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String accountHolder;
    @XmlElement(name = "BankAccountType")
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.BankAccountType bankAccountType;
    @XmlElement(name = "BankCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bankCode;
    @XmlElement(name = "BankName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bankName;
    @XmlElement(name = "BIC")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bic;
    @XmlElement(name = "IBAN")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String iban;
    @XmlElement(name = "Country")
    @XmlSchemaType(name = "token")
    protected CountryType country;
    @XmlElement(name = "VaultId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String vaultId;
    @XmlElement(name = "Vault", defaultValue = "false")
    protected Boolean vault;
    @XmlElement(name = "BudgetPeriod")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String budgetPeriod;
    @XmlElement(name = "Redirect")
    protected RedirectRequestType redirect;
    @XmlElement(name = "Order", required = true)
    protected OrderType order;
    @XmlElement(name = "ThreeDSecure")
    protected ThreeDSecureType threeDSecure;
    @XmlElement(name = "Risk")
    protected RiskType risk;
    @XmlElement(name = "UserDefinedFields")
    protected List<KeyValueType> userDefinedFields;
    @XmlElement(name = "BillingDescriptor")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String billingDescriptor;

    /**
     * Gets the value of the account property.
     *
     * @return possible object is
     * {@link PayGateAccountType }
     */
    public PayGateAccountType getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     *
     * @param value allowed object is
     *              {@link PayGateAccountType }
     */
    public void setAccount(PayGateAccountType value) {
        this.account = value;
    }

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
     * Gets the value of the accountNumber property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the accountHolder property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAccountHolder() {
        return accountHolder;
    }

    /**
     * Sets the value of the accountHolder property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAccountHolder(String value) {
        this.accountHolder = value;
    }

    /**
     * Gets the value of the bankAccountType property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.BankAccountType }
     */
    public za.co.dladle.paygate.BankAccountType getBankAccountType() {
        return bankAccountType;
    }

    /**
     * Sets the value of the bankAccountType property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.BankAccountType }
     */
    public void setBankAccountType(BankAccountType value) {
        this.bankAccountType = value;
    }

    /**
     * Gets the value of the bankCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * Sets the value of the bankCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    /**
     * Gets the value of the bankName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Sets the value of the bankName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     * Gets the value of the bic property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBIC() {
        return bic;
    }

    /**
     * Sets the value of the bic property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBIC(String value) {
        this.bic = value;
    }

    /**
     * Gets the value of the iban property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIBAN() {
        return iban;
    }

    /**
     * Sets the value of the iban property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIBAN(String value) {
        this.iban = value;
    }

    /**
     * Gets the value of the country property.
     *
     * @return possible object is
     * {@link CountryType }
     */
    public CountryType getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     *
     * @param value allowed object is
     *              {@link CountryType }
     */
    public void setCountry(CountryType value) {
        this.country = value;
    }

    /**
     * Gets the value of the vaultId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVaultId() {
        return vaultId;
    }

    /**
     * Sets the value of the vaultId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVaultId(String value) {
        this.vaultId = value;
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
     * Gets the value of the budgetPeriod property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBudgetPeriod() {
        return budgetPeriod;
    }

    /**
     * Sets the value of the budgetPeriod property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBudgetPeriod(String value) {
        this.budgetPeriod = value;
    }

    /**
     * Gets the value of the redirect property.
     *
     * @return possible object is
     * {@link RedirectRequestType }
     */
    public RedirectRequestType getRedirect() {
        return redirect;
    }

    /**
     * Sets the value of the redirect property.
     *
     * @param value allowed object is
     *              {@link RedirectRequestType }
     */
    public void setRedirect(RedirectRequestType value) {
        this.redirect = value;
    }

    /**
     * Gets the value of the order property.
     *
     * @return possible object is
     * {@link OrderType }
     */
    public OrderType getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     *
     * @param value allowed object is
     *              {@link OrderType }
     */
    public void setOrder(OrderType value) {
        this.order = value;
    }

    /**
     * Gets the value of the threeDSecure property.
     *
     * @return possible object is
     * {@link ThreeDSecureType }
     */
    public ThreeDSecureType getThreeDSecure() {
        return threeDSecure;
    }

    /**
     * Sets the value of the threeDSecure property.
     *
     * @param value allowed object is
     *              {@link ThreeDSecureType }
     */
    public void setThreeDSecure(ThreeDSecureType value) {
        this.threeDSecure = value;
    }

    /**
     * Gets the value of the risk property.
     *
     * @return possible object is
     * {@link RiskType }
     */
    public RiskType getRisk() {
        return risk;
    }

    /**
     * Sets the value of the risk property.
     *
     * @param value allowed object is
     *              {@link RiskType }
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
     * {@link KeyValueType }
     */
    public List<KeyValueType> getUserDefinedFields() {
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
