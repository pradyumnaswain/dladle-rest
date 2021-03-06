
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for QueryRequestType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="QueryRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Account" type="{http://www.paygate.co.za/PayHOST}PayGateAccountType"/>
 *         &lt;choice>
 *           &lt;element name="TransactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="PayRequestId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *           &lt;sequence>
 *             &lt;element name="MerchantOrderId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *             &lt;element name="TransactionType" type="{http://www.paygate.co.za/PayHOST}QueryTransactionType" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element name="UserDefinedFields" type="{http://www.paygate.co.za/PayHOST}KeyValueType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryRequestType", propOrder = {
        "account",
        "transactionId",
        "payRequestId",
        "merchantOrderId",
        "transactionType",
        "userDefinedFields"
})
public class QueryRequestType {

    @XmlElement(name = "Account", required = true)
    protected za.co.dladle.paygate.PayGateAccountType account;
    @XmlElement(name = "TransactionId")
    protected String transactionId;
    @XmlElement(name = "PayRequestId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String payRequestId;
    @XmlElement(name = "MerchantOrderId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String merchantOrderId;
    @XmlElement(name = "TransactionType")
    @XmlSchemaType(name = "token")
    protected QueryTransactionType transactionType;
    @XmlElement(name = "UserDefinedFields")
    protected List<za.co.dladle.paygate.KeyValueType> userDefinedFields;

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
     * Gets the value of the transactionId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the payRequestId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPayRequestId() {
        return payRequestId;
    }

    /**
     * Sets the value of the payRequestId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayRequestId(String value) {
        this.payRequestId = value;
    }

    /**
     * Gets the value of the merchantOrderId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    /**
     * Sets the value of the merchantOrderId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMerchantOrderId(String value) {
        this.merchantOrderId = value;
    }

    /**
     * Gets the value of the transactionType property.
     *
     * @return possible object is
     * {@link QueryTransactionType }
     */
    public QueryTransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     *
     * @param value allowed object is
     *              {@link QueryTransactionType }
     */
    public void setTransactionType(QueryTransactionType value) {
        this.transactionType = value;
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

}
