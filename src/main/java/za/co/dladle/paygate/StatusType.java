
package za.co.dladle.paygate;

import za.co.dladle.paygate.*;
import za.co.dladle.paygate.CurrencyType;
import za.co.dladle.paygate.PaymentType;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for StatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcquirerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusName" type="{http://www.paygate.co.za/PayHOST}StatusNameType" minOccurs="0"/>
 *         &lt;element name="StatusDetail" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="AuthCode" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="PayRequestId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="VaultId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="PayVaultData" type="{http://www.paygate.co.za/PayHOST}VaultDataType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TransactionStatusCode" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="TransactionStatusDescription" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="ResultCode" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="ResultDescription" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="Currency" type="{http://www.paygate.co.za/PayHOST}CurrencyType" minOccurs="0"/>
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="RequestedCurrency" type="{http://www.paygate.co.za/PayHOST}CurrencyType" minOccurs="0"/>
 *         &lt;element name="RequestedAmount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ConversionRate" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="RiskIndicator" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="PaymentType" type="{http://www.paygate.co.za/PayHOST}PaymentType" minOccurs="0"/>
 *         &lt;element name="BillingDescriptor" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="DateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="TransactionType" type="{http://www.paygate.co.za/PayHOST}TransactionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusType", propOrder = {
    "transactionId",
    "reference",
    "acquirerCode",
    "statusName",
    "statusDetail",
    "authCode",
    "payRequestId",
    "vaultId",
    "payVaultData",
    "transactionStatusCode",
    "transactionStatusDescription",
    "resultCode",
    "resultDescription",
    "currency",
    "amount",
    "requestedCurrency",
    "requestedAmount",
    "conversionRate",
    "riskIndicator",
    "paymentType",
    "billingDescriptor",
    "dateTime",
    "transactionType"
})
public class StatusType {

    @XmlElement(name = "TransactionId")
    protected String transactionId;
    @XmlElement(name = "Reference")
    protected String reference;
    @XmlElement(name = "AcquirerCode")
    protected String acquirerCode;
    @XmlElement(name = "StatusName")
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.StatusNameType statusName;
    @XmlElement(name = "StatusDetail")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String statusDetail;
    @XmlElement(name = "AuthCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String authCode;
    @XmlElement(name = "PayRequestId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String payRequestId;
    @XmlElement(name = "VaultId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String vaultId;
    @XmlElement(name = "PayVaultData")
    protected List<VaultDataType> payVaultData;
    @XmlElement(name = "TransactionStatusCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String transactionStatusCode;
    @XmlElement(name = "TransactionStatusDescription")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String transactionStatusDescription;
    @XmlElement(name = "ResultCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String resultCode;
    @XmlElement(name = "ResultDescription")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String resultDescription;
    @XmlElement(name = "Currency")
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.CurrencyType currency;
    @XmlElement(name = "Amount")
    protected Integer amount;
    @XmlElement(name = "RequestedCurrency")
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.CurrencyType requestedCurrency;
    @XmlElement(name = "RequestedAmount")
    protected Integer requestedAmount;
    @XmlElement(name = "ConversionRate")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String conversionRate;
    @XmlElement(name = "RiskIndicator")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String riskIndicator;
    @XmlElement(name = "PaymentType")
    protected za.co.dladle.paygate.PaymentType paymentType;
    @XmlElement(name = "BillingDescriptor")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String billingDescriptor;
    @XmlElement(name = "DateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTime;
    @XmlElement(name = "TransactionType")
    @XmlSchemaType(name = "token")
    protected TransactionType transactionType;

    /**
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the acquirerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcquirerCode() {
        return acquirerCode;
    }

    /**
     * Sets the value of the acquirerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcquirerCode(String value) {
        this.acquirerCode = value;
    }

    /**
     * Gets the value of the statusName property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.StatusNameType }
     *     
     */
    public za.co.dladle.paygate.StatusNameType getStatusName() {
        return statusName;
    }

    /**
     * Sets the value of the statusName property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.StatusNameType }
     *     
     */
    public void setStatusName(za.co.dladle.paygate.StatusNameType value) {
        this.statusName = value;
    }

    /**
     * Gets the value of the statusDetail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDetail() {
        return statusDetail;
    }

    /**
     * Sets the value of the statusDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDetail(String value) {
        this.statusDetail = value;
    }

    /**
     * Gets the value of the authCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthCode() {
        return authCode;
    }

    /**
     * Sets the value of the authCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthCode(String value) {
        this.authCode = value;
    }

    /**
     * Gets the value of the payRequestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayRequestId() {
        return payRequestId;
    }

    /**
     * Sets the value of the payRequestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayRequestId(String value) {
        this.payRequestId = value;
    }

    /**
     * Gets the value of the vaultId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaultId() {
        return vaultId;
    }

    /**
     * Sets the value of the vaultId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaultId(String value) {
        this.vaultId = value;
    }

    /**
     * Gets the value of the payVaultData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payVaultData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayVaultData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VaultDataType }
     * 
     * 
     */
    public List<VaultDataType> getPayVaultData() {
        if (payVaultData == null) {
            payVaultData = new ArrayList<VaultDataType>();
        }
        return this.payVaultData;
    }

    /**
     * Gets the value of the transactionStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStatusCode() {
        return transactionStatusCode;
    }

    /**
     * Sets the value of the transactionStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStatusCode(String value) {
        this.transactionStatusCode = value;
    }

    /**
     * Gets the value of the transactionStatusDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStatusDescription() {
        return transactionStatusDescription;
    }

    /**
     * Sets the value of the transactionStatusDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStatusDescription(String value) {
        this.transactionStatusDescription = value;
    }

    /**
     * Gets the value of the resultCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Sets the value of the resultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultCode(String value) {
        this.resultCode = value;
    }

    /**
     * Gets the value of the resultDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultDescription() {
        return resultDescription;
    }

    /**
     * Sets the value of the resultDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultDescription(String value) {
        this.resultDescription = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.CurrencyType }
     *     
     */
    public za.co.dladle.paygate.CurrencyType getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.CurrencyType }
     *     
     */
    public void setCurrency(za.co.dladle.paygate.CurrencyType value) {
        this.currency = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAmount(Integer value) {
        this.amount = value;
    }

    /**
     * Gets the value of the requestedCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.CurrencyType }
     *     
     */
    public za.co.dladle.paygate.CurrencyType getRequestedCurrency() {
        return requestedCurrency;
    }

    /**
     * Sets the value of the requestedCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.CurrencyType }
     *     
     */
    public void setRequestedCurrency(CurrencyType value) {
        this.requestedCurrency = value;
    }

    /**
     * Gets the value of the requestedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    /**
     * Sets the value of the requestedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRequestedAmount(Integer value) {
        this.requestedAmount = value;
    }

    /**
     * Gets the value of the conversionRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConversionRate() {
        return conversionRate;
    }

    /**
     * Sets the value of the conversionRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConversionRate(String value) {
        this.conversionRate = value;
    }

    /**
     * Gets the value of the riskIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskIndicator() {
        return riskIndicator;
    }

    /**
     * Sets the value of the riskIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskIndicator(String value) {
        this.riskIndicator = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.PaymentType }
     *     
     */
    public za.co.dladle.paygate.PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.PaymentType }
     *     
     */
    public void setPaymentType(PaymentType value) {
        this.paymentType = value;
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

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTime(XMLGregorianCalendar value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionType }
     *     
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionType }
     *     
     */
    public void setTransactionType(TransactionType value) {
        this.transactionType = value;
    }

}
