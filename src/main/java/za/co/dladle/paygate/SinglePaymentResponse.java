
package za.co.dladle.paygate;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="CardPaymentResponse" type="{http://www.paygate.co.za/PayHOST}CardPaymentResponseType"/>
 *           &lt;element name="WebPaymentResponse" type="{http://www.paygate.co.za/PayHOST}WebPaymentResponseType"/>
 *           &lt;element name="TokenPaymentResponse" type="{http://www.paygate.co.za/PayHOST}TokenPaymentResponseType"/>
 *           &lt;element name="BankPaymentResponse" type="{http://www.paygate.co.za/PayHOST}BankPaymentResponseType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "cardPaymentResponse",
        "webPaymentResponse",
        "tokenPaymentResponse",
        "bankPaymentResponse"
})
@XmlRootElement(name = "SinglePaymentResponse")
public class SinglePaymentResponse {

    @XmlElement(name = "CardPaymentResponse")
    protected za.co.dladle.paygate.CardPaymentResponseType cardPaymentResponse;
    @XmlElement(name = "WebPaymentResponse")
    protected WebPaymentResponseType webPaymentResponse;
    @XmlElement(name = "TokenPaymentResponse")
    protected TokenPaymentResponseType tokenPaymentResponse;
    @XmlElement(name = "BankPaymentResponse")
    protected za.co.dladle.paygate.BankPaymentResponseType bankPaymentResponse;

    /**
     * Gets the value of the cardPaymentResponse property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.CardPaymentResponseType }
     */
    public za.co.dladle.paygate.CardPaymentResponseType getCardPaymentResponse() {
        return cardPaymentResponse;
    }

    /**
     * Sets the value of the cardPaymentResponse property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.CardPaymentResponseType }
     */
    public void setCardPaymentResponse(za.co.dladle.paygate.CardPaymentResponseType value) {
        this.cardPaymentResponse = value;
    }

    /**
     * Gets the value of the webPaymentResponse property.
     *
     * @return possible object is
     * {@link WebPaymentResponseType }
     */
    public WebPaymentResponseType getWebPaymentResponse() {
        return webPaymentResponse;
    }

    /**
     * Sets the value of the webPaymentResponse property.
     *
     * @param value allowed object is
     *              {@link WebPaymentResponseType }
     */
    public void setWebPaymentResponse(WebPaymentResponseType value) {
        this.webPaymentResponse = value;
    }

    /**
     * Gets the value of the tokenPaymentResponse property.
     *
     * @return possible object is
     * {@link TokenPaymentResponseType }
     */
    public TokenPaymentResponseType getTokenPaymentResponse() {
        return tokenPaymentResponse;
    }

    /**
     * Sets the value of the tokenPaymentResponse property.
     *
     * @param value allowed object is
     *              {@link TokenPaymentResponseType }
     */
    public void setTokenPaymentResponse(TokenPaymentResponseType value) {
        this.tokenPaymentResponse = value;
    }

    /**
     * Gets the value of the bankPaymentResponse property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.BankPaymentResponseType }
     */
    public za.co.dladle.paygate.BankPaymentResponseType getBankPaymentResponse() {
        return bankPaymentResponse;
    }

    /**
     * Sets the value of the bankPaymentResponse property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.BankPaymentResponseType }
     */
    public void setBankPaymentResponse(BankPaymentResponseType value) {
        this.bankPaymentResponse = value;
    }

}
