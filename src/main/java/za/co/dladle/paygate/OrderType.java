
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for OrderType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="OrderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MerchantOrderId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="Currency" type="{http://www.paygate.co.za/PayHOST}CurrencyType"/>
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Discount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="BillingDetails" type="{http://www.paygate.co.za/PayHOST}BillingDetailsType" minOccurs="0"/>
 *         &lt;element name="ShippingDetails" type="{http://www.paygate.co.za/PayHOST}ShippingDetailsType" minOccurs="0"/>
 *         &lt;element name="AirlineBookingDetails" type="{http://www.paygate.co.za/PayHOST}AirlineBookingDetailsType" minOccurs="0"/>
 *         &lt;element name="OrderItems" type="{http://www.paygate.co.za/PayHOST}OrderItemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Locale" type="{http://www.paygate.co.za/PayHOST}LanguageType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderType", propOrder = {
        "merchantOrderId",
        "currency",
        "amount",
        "discount",
        "transactionDate",
        "billingDetails",
        "shippingDetails",
        "airlineBookingDetails",
        "orderItems",
        "locale"
})
public class OrderType {

    @XmlElement(name = "MerchantOrderId", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String merchantOrderId;
    @XmlElement(name = "Currency", required = true)
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.CurrencyType currency;
    @XmlElement(name = "Amount")
    protected int amount;
    @XmlElement(name = "Discount")
    protected Integer discount;
    @XmlElement(name = "TransactionDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transactionDate;
    @XmlElement(name = "BillingDetails")
    protected za.co.dladle.paygate.BillingDetailsType billingDetails;
    @XmlElement(name = "ShippingDetails")
    protected ShippingDetailsType shippingDetails;
    @XmlElement(name = "AirlineBookingDetails")
    protected za.co.dladle.paygate.AirlineBookingDetailsType airlineBookingDetails;
    @XmlElement(name = "OrderItems")
    protected List<za.co.dladle.paygate.OrderItemType> orderItems;
    @XmlElement(name = "Locale")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String locale;

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
     * Gets the value of the currency property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.CurrencyType }
     */
    public za.co.dladle.paygate.CurrencyType getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.CurrencyType }
     */
    public void setCurrency(za.co.dladle.paygate.CurrencyType value) {
        this.currency = value;
    }

    /**
     * Gets the value of the amount property.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     */
    public void setAmount(int value) {
        this.amount = value;
    }

    /**
     * Gets the value of the discount property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setDiscount(Integer value) {
        this.discount = value;
    }

    /**
     * Gets the value of the transactionDate property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the billingDetails property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.BillingDetailsType }
     */
    public za.co.dladle.paygate.BillingDetailsType getBillingDetails() {
        return billingDetails;
    }

    /**
     * Sets the value of the billingDetails property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.BillingDetailsType }
     */
    public void setBillingDetails(BillingDetailsType value) {
        this.billingDetails = value;
    }

    /**
     * Gets the value of the shippingDetails property.
     *
     * @return possible object is
     * {@link ShippingDetailsType }
     */
    public ShippingDetailsType getShippingDetails() {
        return shippingDetails;
    }

    /**
     * Sets the value of the shippingDetails property.
     *
     * @param value allowed object is
     *              {@link ShippingDetailsType }
     */
    public void setShippingDetails(ShippingDetailsType value) {
        this.shippingDetails = value;
    }

    /**
     * Gets the value of the airlineBookingDetails property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.AirlineBookingDetailsType }
     */
    public za.co.dladle.paygate.AirlineBookingDetailsType getAirlineBookingDetails() {
        return airlineBookingDetails;
    }

    /**
     * Sets the value of the airlineBookingDetails property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.AirlineBookingDetailsType }
     */
    public void setAirlineBookingDetails(AirlineBookingDetailsType value) {
        this.airlineBookingDetails = value;
    }

    /**
     * Gets the value of the orderItems property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderItems property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderItems().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link za.co.dladle.paygate.OrderItemType }
     */
    public List<za.co.dladle.paygate.OrderItemType> getOrderItems() {
        if (orderItems == null) {
            orderItems = new ArrayList<OrderItemType>();
        }
        return this.orderItems;
    }

    /**
     * Gets the value of the locale property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLocale(String value) {
        this.locale = value;
    }

}
