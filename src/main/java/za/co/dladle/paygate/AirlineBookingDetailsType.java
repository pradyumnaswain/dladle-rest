
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for AirlineBookingDetailsType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="AirlineBookingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TicketNumber" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="InternalCustomerCode" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="ReservationSystem" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="TravelAgencyCode" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="TravelAgencyName" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="PayerTravelling" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PNR" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="Passengers" type="{http://www.paygate.co.za/PayHOST}PassengerType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FlightLegs" type="{http://www.paygate.co.za/PayHOST}FlightLegType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirlineBookingDetailsType", propOrder = {
        "ticketNumber",
        "internalCustomerCode",
        "reservationSystem",
        "travelAgencyCode",
        "travelAgencyName",
        "payerTravelling",
        "pnr",
        "passengers",
        "flightLegs"
})
public class AirlineBookingDetailsType {

    @XmlElement(name = "TicketNumber")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ticketNumber;
    @XmlElement(name = "InternalCustomerCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String internalCustomerCode;
    @XmlElement(name = "ReservationSystem")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String reservationSystem;
    @XmlElement(name = "TravelAgencyCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String travelAgencyCode;
    @XmlElement(name = "TravelAgencyName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String travelAgencyName;
    @XmlElement(name = "PayerTravelling")
    protected Boolean payerTravelling;
    @XmlElement(name = "PNR", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String pnr;
    @XmlElement(name = "Passengers")
    protected List<PassengerType> passengers;
    @XmlElement(name = "FlightLegs")
    protected List<FlightLegType> flightLegs;

    /**
     * Gets the value of the ticketNumber property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Sets the value of the ticketNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTicketNumber(String value) {
        this.ticketNumber = value;
    }

    /**
     * Gets the value of the internalCustomerCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInternalCustomerCode() {
        return internalCustomerCode;
    }

    /**
     * Sets the value of the internalCustomerCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInternalCustomerCode(String value) {
        this.internalCustomerCode = value;
    }

    /**
     * Gets the value of the reservationSystem property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getReservationSystem() {
        return reservationSystem;
    }

    /**
     * Sets the value of the reservationSystem property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReservationSystem(String value) {
        this.reservationSystem = value;
    }

    /**
     * Gets the value of the travelAgencyCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTravelAgencyCode() {
        return travelAgencyCode;
    }

    /**
     * Sets the value of the travelAgencyCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTravelAgencyCode(String value) {
        this.travelAgencyCode = value;
    }

    /**
     * Gets the value of the travelAgencyName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTravelAgencyName() {
        return travelAgencyName;
    }

    /**
     * Sets the value of the travelAgencyName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTravelAgencyName(String value) {
        this.travelAgencyName = value;
    }

    /**
     * Gets the value of the payerTravelling property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isPayerTravelling() {
        return payerTravelling;
    }

    /**
     * Sets the value of the payerTravelling property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setPayerTravelling(Boolean value) {
        this.payerTravelling = value;
    }

    /**
     * Gets the value of the pnr property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPNR() {
        return pnr;
    }

    /**
     * Sets the value of the pnr property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPNR(String value) {
        this.pnr = value;
    }

    /**
     * Gets the value of the passengers property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the passengers property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPassengers().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PassengerType }
     */
    public List<PassengerType> getPassengers() {
        if (passengers == null) {
            passengers = new ArrayList<PassengerType>();
        }
        return this.passengers;
    }

    /**
     * Gets the value of the flightLegs property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flightLegs property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlightLegs().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightLegType }
     */
    public List<FlightLegType> getFlightLegs() {
        if (flightLegs == null) {
            flightLegs = new ArrayList<FlightLegType>();
        }
        return this.flightLegs;
    }

}
