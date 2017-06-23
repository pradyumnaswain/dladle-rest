
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for PassengerType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="PassengerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Passenger" type="{http://www.paygate.co.za/PayHOST}PersonType"/>
 *         &lt;element name="TravellerType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="C"/>
 *               &lt;enumeration value="T"/>
 *               &lt;enumeration value="I"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LoyaltyNumber" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="LoyaltyType" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="LoyaltyTier" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PassengerType", propOrder = {
        "passenger",
        "travellerType",
        "loyaltyNumber",
        "loyaltyType",
        "loyaltyTier"
})
public class PassengerType {

    @XmlElement(name = "Passenger", required = true)
    protected PersonType passenger;
    @XmlElement(name = "TravellerType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String travellerType;
    @XmlElement(name = "LoyaltyNumber")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String loyaltyNumber;
    @XmlElement(name = "LoyaltyType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String loyaltyType;
    @XmlElement(name = "LoyaltyTier")
    protected Integer loyaltyTier;

    /**
     * Gets the value of the passenger property.
     *
     * @return possible object is
     * {@link PersonType }
     */
    public PersonType getPassenger() {
        return passenger;
    }

    /**
     * Sets the value of the passenger property.
     *
     * @param value allowed object is
     *              {@link PersonType }
     */
    public void setPassenger(PersonType value) {
        this.passenger = value;
    }

    /**
     * Gets the value of the travellerType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTravellerType() {
        return travellerType;
    }

    /**
     * Sets the value of the travellerType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTravellerType(String value) {
        this.travellerType = value;
    }

    /**
     * Gets the value of the loyaltyNumber property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLoyaltyNumber() {
        return loyaltyNumber;
    }

    /**
     * Sets the value of the loyaltyNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLoyaltyNumber(String value) {
        this.loyaltyNumber = value;
    }

    /**
     * Gets the value of the loyaltyType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLoyaltyType() {
        return loyaltyType;
    }

    /**
     * Sets the value of the loyaltyType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLoyaltyType(String value) {
        this.loyaltyType = value;
    }

    /**
     * Gets the value of the loyaltyTier property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getLoyaltyTier() {
        return loyaltyTier;
    }

    /**
     * Sets the value of the loyaltyTier property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setLoyaltyTier(Integer value) {
        this.loyaltyTier = value;
    }

}
