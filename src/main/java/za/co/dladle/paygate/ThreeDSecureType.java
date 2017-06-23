
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ThreeDSecureType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="ThreeDSecureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Enrolled" type="{http://www.paygate.co.za/PayHOST}YNU"/>
 *         &lt;element name="Paresstatus" type="{http://www.paygate.co.za/PayHOST}YNUA"/>
 *         &lt;element name="Eci" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="Xid" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="Cavv" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThreeDSecureType", propOrder = {
        "enrolled",
        "paresstatus",
        "eci",
        "xid",
        "cavv"
})
public class ThreeDSecureType {

    @XmlElement(name = "Enrolled", required = true)
    @XmlSchemaType(name = "token")
    protected YNU enrolled;
    @XmlElement(name = "Paresstatus", required = true)
    @XmlSchemaType(name = "token")
    protected za.co.dladle.paygate.YNUA paresstatus;
    @XmlElement(name = "Eci", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String eci;
    @XmlElement(name = "Xid", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String xid;
    @XmlElement(name = "Cavv", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String cavv;

    /**
     * Gets the value of the enrolled property.
     *
     * @return possible object is
     * {@link YNU }
     */
    public YNU getEnrolled() {
        return enrolled;
    }

    /**
     * Sets the value of the enrolled property.
     *
     * @param value allowed object is
     *              {@link YNU }
     */
    public void setEnrolled(YNU value) {
        this.enrolled = value;
    }

    /**
     * Gets the value of the paresstatus property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.YNUA }
     */
    public za.co.dladle.paygate.YNUA getParesstatus() {
        return paresstatus;
    }

    /**
     * Sets the value of the paresstatus property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.YNUA }
     */
    public void setParesstatus(za.co.dladle.paygate.YNUA value) {
        this.paresstatus = value;
    }

    /**
     * Gets the value of the eci property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEci() {
        return eci;
    }

    /**
     * Sets the value of the eci property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEci(String value) {
        this.eci = value;
    }

    /**
     * Gets the value of the xid property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getXid() {
        return xid;
    }

    /**
     * Sets the value of the xid property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setXid(String value) {
        this.xid = value;
    }

    /**
     * Gets the value of the cavv property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCavv() {
        return cavv;
    }

    /**
     * Sets the value of the cavv property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCavv(String value) {
        this.cavv = value;
    }

}
