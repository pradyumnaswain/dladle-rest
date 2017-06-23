
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for RedirectResponseType complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="RedirectResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RedirectUrl" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="UrlParams" type="{http://www.paygate.co.za/PayHOST}KeyValueType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RedirectResponseType", propOrder = {
        "redirectUrl",
        "urlParams"
})
public class RedirectResponseType {

    @XmlElement(name = "RedirectUrl", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String redirectUrl;
    @XmlElement(name = "UrlParams")
    protected List<za.co.dladle.paygate.KeyValueType> urlParams;

    /**
     * Gets the value of the redirectUrl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the value of the redirectUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRedirectUrl(String value) {
        this.redirectUrl = value;
    }

    /**
     * Gets the value of the urlParams property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the urlParams property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUrlParams().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link za.co.dladle.paygate.KeyValueType }
     */
    public List<za.co.dladle.paygate.KeyValueType> getUrlParams() {
        if (urlParams == null) {
            urlParams = new ArrayList<za.co.dladle.paygate.KeyValueType>();
        }
        return this.urlParams;
    }

}
