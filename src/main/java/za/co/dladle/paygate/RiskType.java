
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for RiskType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="RiskType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="SessionId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="IpV4Address" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="IpV6Address" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="MachineId" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *         &lt;element name="UserProfile" type="{http://www.w3.org/2001/XMLSchema}token" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ConsumerWatch" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Browser" type="{http://www.paygate.co.za/PayHOST}BrowserType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiskType", propOrder = {
        "accountNumber",
        "sessionId",
        "ipV4Address",
        "ipV6Address",
        "userId",
        "machineId",
        "userProfile",
        "consumerWatch",
        "browser"
})
public class RiskType {

    @XmlElement(name = "AccountNumber")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String accountNumber;
    @XmlElement(name = "SessionId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String sessionId;
    @XmlElement(name = "IpV4Address")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ipV4Address;
    @XmlElement(name = "IpV6Address")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String ipV6Address;
    @XmlElement(name = "UserId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String userId;
    @XmlElement(name = "MachineId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String machineId;
    @XmlElement(name = "UserProfile")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> userProfile;
    @XmlElement(name = "ConsumerWatch")
    protected Boolean consumerWatch;
    @XmlElement(name = "Browser")
    protected za.co.dladle.paygate.BrowserType browser;

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
     * Gets the value of the sessionId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the ipV4Address property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIpV4Address() {
        return ipV4Address;
    }

    /**
     * Sets the value of the ipV4Address property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIpV4Address(String value) {
        this.ipV4Address = value;
    }

    /**
     * Gets the value of the ipV6Address property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIpV6Address() {
        return ipV6Address;
    }

    /**
     * Sets the value of the ipV6Address property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIpV6Address(String value) {
        this.ipV6Address = value;
    }

    /**
     * Gets the value of the userId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the machineId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMachineId() {
        return machineId;
    }

    /**
     * Sets the value of the machineId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMachineId(String value) {
        this.machineId = value;
    }

    /**
     * Gets the value of the userProfile property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userProfile property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserProfile().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getUserProfile() {
        if (userProfile == null) {
            userProfile = new ArrayList<String>();
        }
        return this.userProfile;
    }

    /**
     * Gets the value of the consumerWatch property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isConsumerWatch() {
        return consumerWatch;
    }

    /**
     * Sets the value of the consumerWatch property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setConsumerWatch(Boolean value) {
        this.consumerWatch = value;
    }

    /**
     * Gets the value of the browser property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.BrowserType }
     */
    public za.co.dladle.paygate.BrowserType getBrowser() {
        return browser;
    }

    /**
     * Sets the value of the browser property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.BrowserType }
     */
    public void setBrowser(za.co.dladle.paygate.BrowserType value) {
        this.browser = value;
    }

}
