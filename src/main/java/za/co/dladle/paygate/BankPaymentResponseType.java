
package za.co.dladle.paygate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for BankPaymentResponseType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="BankPaymentResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://www.paygate.co.za/PayHOST}StatusType"/>
 *         &lt;element name="Redirect" type="{http://www.paygate.co.za/PayHOST}RedirectResponseType" minOccurs="0"/>
 *         &lt;element name="Instructions" type="{http://www.paygate.co.za/PayHOST}InstructionsResponseType" minOccurs="0"/>
 *         &lt;element name="UserDefinedFields" type="{http://www.paygate.co.za/PayHOST}KeyValueType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankPaymentResponseType", propOrder = {
        "status",
        "redirect",
        "instructions",
        "userDefinedFields"
})
public class BankPaymentResponseType {

    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "Redirect")
    protected RedirectResponseType redirect;
    @XmlElement(name = "Instructions")
    protected InstructionsResponseType instructions;
    @XmlElement(name = "UserDefinedFields")
    protected List<KeyValueType> userDefinedFields;

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     * {@link StatusType }
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link StatusType }
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the redirect property.
     *
     * @return possible object is
     * {@link RedirectResponseType }
     */
    public RedirectResponseType getRedirect() {
        return redirect;
    }

    /**
     * Sets the value of the redirect property.
     *
     * @param value allowed object is
     *              {@link RedirectResponseType }
     */
    public void setRedirect(RedirectResponseType value) {
        this.redirect = value;
    }

    /**
     * Gets the value of the instructions property.
     *
     * @return possible object is
     * {@link InstructionsResponseType }
     */
    public InstructionsResponseType getInstructions() {
        return instructions;
    }

    /**
     * Sets the value of the instructions property.
     *
     * @param value allowed object is
     *              {@link InstructionsResponseType }
     */
    public void setInstructions(InstructionsResponseType value) {
        this.instructions = value;
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

}
