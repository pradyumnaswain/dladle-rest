
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
 *           &lt;element name="CardPayoutResponse" type="{http://www.paygate.co.za/PayHOST}CardPayoutResponseType"/>
 *           &lt;element name="BankPayoutResponse" type="{http://www.paygate.co.za/PayHOST}BankPayoutResponseType"/>
 *           &lt;element name="WalletPayoutResponse" type="{http://www.paygate.co.za/PayHOST}WalletPayoutResponseType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "cardPayoutResponse",
        "bankPayoutResponse",
        "walletPayoutResponse"
})
@XmlRootElement(name = "SinglePayoutResponse")
public class SinglePayoutResponse {

    @XmlElement(name = "CardPayoutResponse")
    protected za.co.dladle.paygate.CardPayoutResponseType cardPayoutResponse;
    @XmlElement(name = "BankPayoutResponse")
    protected za.co.dladle.paygate.BankPayoutResponseType bankPayoutResponse;
    @XmlElement(name = "WalletPayoutResponse")
    protected WalletPayoutResponseType walletPayoutResponse;

    /**
     * Gets the value of the cardPayoutResponse property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.CardPayoutResponseType }
     */
    public za.co.dladle.paygate.CardPayoutResponseType getCardPayoutResponse() {
        return cardPayoutResponse;
    }

    /**
     * Sets the value of the cardPayoutResponse property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.CardPayoutResponseType }
     */
    public void setCardPayoutResponse(za.co.dladle.paygate.CardPayoutResponseType value) {
        this.cardPayoutResponse = value;
    }

    /**
     * Gets the value of the bankPayoutResponse property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.BankPayoutResponseType }
     */
    public za.co.dladle.paygate.BankPayoutResponseType getBankPayoutResponse() {
        return bankPayoutResponse;
    }

    /**
     * Sets the value of the bankPayoutResponse property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.BankPayoutResponseType }
     */
    public void setBankPayoutResponse(BankPayoutResponseType value) {
        this.bankPayoutResponse = value;
    }

    /**
     * Gets the value of the walletPayoutResponse property.
     *
     * @return possible object is
     * {@link WalletPayoutResponseType }
     */
    public WalletPayoutResponseType getWalletPayoutResponse() {
        return walletPayoutResponse;
    }

    /**
     * Sets the value of the walletPayoutResponse property.
     *
     * @param value allowed object is
     *              {@link WalletPayoutResponseType }
     */
    public void setWalletPayoutResponse(WalletPayoutResponseType value) {
        this.walletPayoutResponse = value;
    }

}
