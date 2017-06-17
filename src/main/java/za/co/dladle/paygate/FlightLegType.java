
package za.co.dladle.paygate;

import za.co.dladle.paygate.*;
import za.co.dladle.paygate.CurrencyType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for FlightLegType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlightLegType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DepartureAirport">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[A-Z]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DepartureCountry" type="{http://www.paygate.co.za/PayHOST}CountryType"/>
 *         &lt;element name="DepartureCity">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[A-Z]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DepartureDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DepartureAirportTimeZone" type="{http://www.paygate.co.za/PayHOST}TimeZoneType" minOccurs="0"/>
 *         &lt;element name="ArrivalAirport">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[A-Z]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ArrivalCountry" type="{http://www.paygate.co.za/PayHOST}CountryType"/>
 *         &lt;element name="ArrivalCity">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;pattern value="[A-Z]{3}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ArrivalDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ArrivalAirportTimeZone" type="{http://www.paygate.co.za/PayHOST}TimeZoneType" minOccurs="0"/>
 *         &lt;element name="MarketingCarrierCode" type="{http://www.paygate.co.za/PayHOST}CarrierCodeType"/>
 *         &lt;element name="MarketingCarrierName" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="IssuingCarrierCode" type="{http://www.paygate.co.za/PayHOST}CarrierCodeType"/>
 *         &lt;element name="IssuingCarrierName" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="FlightNumber" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="FareBasisCode" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="FareClass" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="BaseFareAmount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BaseFareCurrency" type="{http://www.paygate.co.za/PayHOST}CurrencyType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightLegType", propOrder = {
    "departureAirport",
    "departureCountry",
    "departureCity",
    "departureDateTime",
    "departureAirportTimeZone",
    "arrivalAirport",
    "arrivalCountry",
    "arrivalCity",
    "arrivalDateTime",
    "arrivalAirportTimeZone",
    "marketingCarrierCode",
    "marketingCarrierName",
    "issuingCarrierCode",
    "issuingCarrierName",
    "flightNumber",
    "fareBasisCode",
    "fareClass",
    "baseFareAmount",
    "baseFareCurrency"
})
public class FlightLegType {

    @XmlElement(name = "DepartureAirport", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String departureAirport;
    @XmlElement(name = "DepartureCountry", required = true)
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.CountryType departureCountry;
    @XmlElement(name = "DepartureCity", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String departureCity;
    @XmlElement(name = "DepartureDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar departureDateTime;
    @XmlElement(name = "DepartureAirportTimeZone")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String departureAirportTimeZone;
    @XmlElement(name = "ArrivalAirport", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String arrivalAirport;
    @XmlElement(name = "ArrivalCountry", required = true)
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.CountryType arrivalCountry;
    @XmlElement(name = "ArrivalCity", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String arrivalCity;
    @XmlElement(name = "ArrivalDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar arrivalDateTime;
    @XmlElement(name = "ArrivalAirportTimeZone")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String arrivalAirportTimeZone;
    @XmlElement(name = "MarketingCarrierCode", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String marketingCarrierCode;
    @XmlElement(name = "MarketingCarrierName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String marketingCarrierName;
    @XmlElement(name = "IssuingCarrierCode", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String issuingCarrierCode;
    @XmlElement(name = "IssuingCarrierName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String issuingCarrierName;
    @XmlElement(name = "FlightNumber", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String flightNumber;
    @XmlElement(name = "FareBasisCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String fareBasisCode;
    @XmlElement(name = "FareClass")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String fareClass;
    @XmlElement(name = "BaseFareAmount")
    protected int baseFareAmount;
    @XmlElement(name = "BaseFareCurrency", required = true)
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.CurrencyType baseFareCurrency;

    /**
     * Gets the value of the departureAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureAirport() {
        return departureAirport;
    }

    /**
     * Sets the value of the departureAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureAirport(String value) {
        this.departureAirport = value;
    }

    /**
     * Gets the value of the departureCountry property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.CountryType }
     *     
     */
    public za.co.dladle.paygate.CountryType getDepartureCountry() {
        return departureCountry;
    }

    /**
     * Sets the value of the departureCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.CountryType }
     *     
     */
    public void setDepartureCountry(za.co.dladle.paygate.CountryType value) {
        this.departureCountry = value;
    }

    /**
     * Gets the value of the departureCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureCity() {
        return departureCity;
    }

    /**
     * Sets the value of the departureCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureCity(String value) {
        this.departureCity = value;
    }

    /**
     * Gets the value of the departureDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDepartureDateTime() {
        return departureDateTime;
    }

    /**
     * Sets the value of the departureDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDepartureDateTime(XMLGregorianCalendar value) {
        this.departureDateTime = value;
    }

    /**
     * Gets the value of the departureAirportTimeZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureAirportTimeZone() {
        return departureAirportTimeZone;
    }

    /**
     * Sets the value of the departureAirportTimeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureAirportTimeZone(String value) {
        this.departureAirportTimeZone = value;
    }

    /**
     * Gets the value of the arrivalAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * Sets the value of the arrivalAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalAirport(String value) {
        this.arrivalAirport = value;
    }

    /**
     * Gets the value of the arrivalCountry property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.CountryType }
     *     
     */
    public za.co.dladle.paygate.CountryType getArrivalCountry() {
        return arrivalCountry;
    }

    /**
     * Sets the value of the arrivalCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.CountryType }
     *     
     */
    public void setArrivalCountry(za.co.dladle.paygate.CountryType value) {
        this.arrivalCountry = value;
    }

    /**
     * Gets the value of the arrivalCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalCity() {
        return arrivalCity;
    }

    /**
     * Sets the value of the arrivalCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalCity(String value) {
        this.arrivalCity = value;
    }

    /**
     * Gets the value of the arrivalDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * Sets the value of the arrivalDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrivalDateTime(XMLGregorianCalendar value) {
        this.arrivalDateTime = value;
    }

    /**
     * Gets the value of the arrivalAirportTimeZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalAirportTimeZone() {
        return arrivalAirportTimeZone;
    }

    /**
     * Sets the value of the arrivalAirportTimeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalAirportTimeZone(String value) {
        this.arrivalAirportTimeZone = value;
    }

    /**
     * Gets the value of the marketingCarrierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketingCarrierCode() {
        return marketingCarrierCode;
    }

    /**
     * Sets the value of the marketingCarrierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketingCarrierCode(String value) {
        this.marketingCarrierCode = value;
    }

    /**
     * Gets the value of the marketingCarrierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketingCarrierName() {
        return marketingCarrierName;
    }

    /**
     * Sets the value of the marketingCarrierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketingCarrierName(String value) {
        this.marketingCarrierName = value;
    }

    /**
     * Gets the value of the issuingCarrierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuingCarrierCode() {
        return issuingCarrierCode;
    }

    /**
     * Sets the value of the issuingCarrierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuingCarrierCode(String value) {
        this.issuingCarrierCode = value;
    }

    /**
     * Gets the value of the issuingCarrierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuingCarrierName() {
        return issuingCarrierName;
    }

    /**
     * Sets the value of the issuingCarrierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuingCarrierName(String value) {
        this.issuingCarrierName = value;
    }

    /**
     * Gets the value of the flightNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the value of the flightNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightNumber(String value) {
        this.flightNumber = value;
    }

    /**
     * Gets the value of the fareBasisCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFareBasisCode() {
        return fareBasisCode;
    }

    /**
     * Sets the value of the fareBasisCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFareBasisCode(String value) {
        this.fareBasisCode = value;
    }

    /**
     * Gets the value of the fareClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFareClass() {
        return fareClass;
    }

    /**
     * Sets the value of the fareClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFareClass(String value) {
        this.fareClass = value;
    }

    /**
     * Gets the value of the baseFareAmount property.
     * 
     */
    public int getBaseFareAmount() {
        return baseFareAmount;
    }

    /**
     * Sets the value of the baseFareAmount property.
     * 
     */
    public void setBaseFareAmount(int value) {
        this.baseFareAmount = value;
    }

    /**
     * Gets the value of the baseFareCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.CurrencyType }
     *     
     */
    public za.co.dladle.paygate.CurrencyType getBaseFareCurrency() {
        return baseFareCurrency;
    }

    /**
     * Sets the value of the baseFareCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.CurrencyType }
     *     
     */
    public void setBaseFareCurrency(CurrencyType value) {
        this.baseFareCurrency = value;
    }

}
