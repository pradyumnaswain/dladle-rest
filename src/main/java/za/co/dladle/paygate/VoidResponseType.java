
package za.co.dladle.paygate;

import za.co.dladle.paygate.*;
import za.co.dladle.paygate.KeyValueType;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VoidResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VoidResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;sequence>
 *           &lt;element name="Status" type="{http://www.paygate.co.za/PayHOST}StatusType"/>
 *           &lt;element name="UserDefinedFields" type="{http://www.paygate.co.za/PayHOST}KeyValueType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoidResponseType", propOrder = {
    "status",
    "userDefinedFields"
})
public class VoidResponseType {

    @XmlElement(name = "Status", required = true)
    protected za.co.dladle.paygate.StatusType status;
    @XmlElement(name = "UserDefinedFields")
    protected List<za.co.dladle.paygate.KeyValueType> userDefinedFields;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link za.co.dladle.paygate.StatusType }
     *     
     */
    public za.co.dladle.paygate.StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link za.co.dladle.paygate.StatusType }
     *     
     */
    public void setStatus(za.co.dladle.paygate.StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the userDefinedFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userDefinedFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserDefinedFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link za.co.dladle.paygate.KeyValueType }
     * 
     * 
     */
    public List<za.co.dladle.paygate.KeyValueType> getUserDefinedFields() {
        if (userDefinedFields == null) {
            userDefinedFields = new ArrayList<KeyValueType>();
        }
        return this.userDefinedFields;
    }

}
