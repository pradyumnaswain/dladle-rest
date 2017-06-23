
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
 *           &lt;element name="QueryRequest" type="{http://www.paygate.co.za/PayHOST}QueryRequestType"/>
 *           &lt;element name="SettlementRequest" type="{http://www.paygate.co.za/PayHOST}SettleRequestType"/>
 *           &lt;element name="RefundRequest" type="{http://www.paygate.co.za/PayHOST}RefundRequestType"/>
 *           &lt;element name="VoidRequest" type="{http://www.paygate.co.za/PayHOST}VoidRequestType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "queryRequest",
        "settlementRequest",
        "refundRequest",
        "voidRequest"
})
@XmlRootElement(name = "SingleFollowUpRequest")
public class SingleFollowUpRequest {

    @XmlElement(name = "QueryRequest")
    protected za.co.dladle.paygate.QueryRequestType queryRequest;
    @XmlElement(name = "SettlementRequest")
    protected za.co.dladle.paygate.SettleRequestType settlementRequest;
    @XmlElement(name = "RefundRequest")
    protected za.co.dladle.paygate.RefundRequestType refundRequest;
    @XmlElement(name = "VoidRequest")
    protected VoidRequestType voidRequest;

    /**
     * Gets the value of the queryRequest property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.QueryRequestType }
     */
    public za.co.dladle.paygate.QueryRequestType getQueryRequest() {
        return queryRequest;
    }

    /**
     * Sets the value of the queryRequest property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.QueryRequestType }
     */
    public void setQueryRequest(za.co.dladle.paygate.QueryRequestType value) {
        this.queryRequest = value;
    }

    /**
     * Gets the value of the settlementRequest property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.SettleRequestType }
     */
    public za.co.dladle.paygate.SettleRequestType getSettlementRequest() {
        return settlementRequest;
    }

    /**
     * Sets the value of the settlementRequest property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.SettleRequestType }
     */
    public void setSettlementRequest(SettleRequestType value) {
        this.settlementRequest = value;
    }

    /**
     * Gets the value of the refundRequest property.
     *
     * @return possible object is
     * {@link za.co.dladle.paygate.RefundRequestType }
     */
    public za.co.dladle.paygate.RefundRequestType getRefundRequest() {
        return refundRequest;
    }

    /**
     * Sets the value of the refundRequest property.
     *
     * @param value allowed object is
     *              {@link za.co.dladle.paygate.RefundRequestType }
     */
    public void setRefundRequest(RefundRequestType value) {
        this.refundRequest = value;
    }

    /**
     * Gets the value of the voidRequest property.
     *
     * @return possible object is
     * {@link VoidRequestType }
     */
    public VoidRequestType getVoidRequest() {
        return voidRequest;
    }

    /**
     * Sets the value of the voidRequest property.
     *
     * @param value allowed object is
     *              {@link VoidRequestType }
     */
    public void setVoidRequest(VoidRequestType value) {
        this.voidRequest = value;
    }

}
