
package za.co.dladle.paygate;

import za.co.dladle.paygate.*;
import za.co.dladle.paygate.AddressType;
import za.co.dladle.paygate.AirlineBookingDetailsType;
import za.co.dladle.paygate.BankPaymentRequestType;
import za.co.dladle.paygate.BankPaymentResponseType;
import za.co.dladle.paygate.BankPayoutRequestType;
import za.co.dladle.paygate.BankPayoutResponseType;
import za.co.dladle.paygate.BillingDetailsType;
import za.co.dladle.paygate.BrowserType;
import za.co.dladle.paygate.CardPaymentRequestType;
import za.co.dladle.paygate.CardPayoutRequestType;
import za.co.dladle.paygate.CardPayoutResponseType;
import za.co.dladle.paygate.CardVaultRequestType;
import za.co.dladle.paygate.CardVaultResponseType;
import za.co.dladle.paygate.DeleteVaultRequestType;
import za.co.dladle.paygate.DeleteVaultResponseType;
import za.co.dladle.paygate.FlightLegType;
import za.co.dladle.paygate.InstructionsResponseType;
import za.co.dladle.paygate.KeyValueType;
import za.co.dladle.paygate.LookUpVaultRequestType;
import za.co.dladle.paygate.LookUpVaultResponseType;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the za.co.dladle.paygate package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: za.co.dladle.paygate
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SinglePaymentResponse }
     * 
     */
    public SinglePaymentResponse createSinglePaymentResponse() {
        return new SinglePaymentResponse();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.CardPaymentResponseType }
     * 
     */
    public za.co.dladle.paygate.CardPaymentResponseType createCardPaymentResponseType() {
        return new za.co.dladle.paygate.CardPaymentResponseType();
    }

    /**
     * Create an instance of {@link WebPaymentResponseType }
     * 
     */
    public WebPaymentResponseType createWebPaymentResponseType() {
        return new WebPaymentResponseType();
    }

    /**
     * Create an instance of {@link TokenPaymentResponseType }
     * 
     */
    public TokenPaymentResponseType createTokenPaymentResponseType() {
        return new TokenPaymentResponseType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.BankPaymentResponseType }
     * 
     */
    public za.co.dladle.paygate.BankPaymentResponseType createBankPaymentResponseType() {
        return new BankPaymentResponseType();
    }

    /**
     * Create an instance of {@link SingleVaultResponse }
     * 
     */
    public SingleVaultResponse createSingleVaultResponse() {
        return new SingleVaultResponse();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.CardVaultResponseType }
     * 
     */
    public za.co.dladle.paygate.CardVaultResponseType createCardVaultResponseType() {
        return new CardVaultResponseType();
    }

    /**
     * Create an instance of {@link WalletVaultResponseType }
     * 
     */
    public WalletVaultResponseType createWalletVaultResponseType() {
        return new WalletVaultResponseType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.LookUpVaultResponseType }
     * 
     */
    public za.co.dladle.paygate.LookUpVaultResponseType createLookUpVaultResponseType() {
        return new LookUpVaultResponseType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.DeleteVaultResponseType }
     * 
     */
    public za.co.dladle.paygate.DeleteVaultResponseType createDeleteVaultResponseType() {
        return new DeleteVaultResponseType();
    }

    /**
     * Create an instance of {@link SingleVaultRequest }
     * 
     */
    public SingleVaultRequest createSingleVaultRequest() {
        return new SingleVaultRequest();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.CardVaultRequestType }
     * 
     */
    public za.co.dladle.paygate.CardVaultRequestType createCardVaultRequestType() {
        return new CardVaultRequestType();
    }

    /**
     * Create an instance of {@link WalletVaultRequestType }
     * 
     */
    public WalletVaultRequestType createWalletVaultRequestType() {
        return new WalletVaultRequestType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.LookUpVaultRequestType }
     * 
     */
    public za.co.dladle.paygate.LookUpVaultRequestType createLookUpVaultRequestType() {
        return new LookUpVaultRequestType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.DeleteVaultRequestType }
     * 
     */
    public za.co.dladle.paygate.DeleteVaultRequestType createDeleteVaultRequestType() {
        return new DeleteVaultRequestType();
    }

    /**
     * Create an instance of {@link PingRequest }
     * 
     */
    public PingRequest createPingRequest() {
        return new PingRequest();
    }

    /**
     * Create an instance of {@link PayGateAccountType }
     * 
     */
    public PayGateAccountType createPayGateAccountType() {
        return new PayGateAccountType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.KeyValueType }
     * 
     */
    public za.co.dladle.paygate.KeyValueType createKeyValueType() {
        return new KeyValueType();
    }

    /**
     * Create an instance of {@link SingleFollowUpRequest }
     * 
     */
    public SingleFollowUpRequest createSingleFollowUpRequest() {
        return new SingleFollowUpRequest();
    }

    /**
     * Create an instance of {@link QueryRequestType }
     * 
     */
    public QueryRequestType createQueryRequestType() {
        return new QueryRequestType();
    }

    /**
     * Create an instance of {@link SettleRequestType }
     * 
     */
    public SettleRequestType createSettleRequestType() {
        return new SettleRequestType();
    }

    /**
     * Create an instance of {@link RefundRequestType }
     * 
     */
    public RefundRequestType createRefundRequestType() {
        return new RefundRequestType();
    }

    /**
     * Create an instance of {@link VoidRequestType }
     * 
     */
    public VoidRequestType createVoidRequestType() {
        return new VoidRequestType();
    }

    /**
     * Create an instance of {@link SingleFollowUpResponse }
     * 
     */
    public SingleFollowUpResponse createSingleFollowUpResponse() {
        return new SingleFollowUpResponse();
    }

    /**
     * Create an instance of {@link QueryResponseType }
     * 
     */
    public QueryResponseType createQueryResponseType() {
        return new QueryResponseType();
    }

    /**
     * Create an instance of {@link SettleResponseType }
     * 
     */
    public SettleResponseType createSettleResponseType() {
        return new SettleResponseType();
    }

    /**
     * Create an instance of {@link RefundResponseType }
     * 
     */
    public RefundResponseType createRefundResponseType() {
        return new RefundResponseType();
    }

    /**
     * Create an instance of {@link VoidResponseType }
     * 
     */
    public VoidResponseType createVoidResponseType() {
        return new VoidResponseType();
    }

    /**
     * Create an instance of {@link SinglePaymentRequest }
     * 
     */
    public SinglePaymentRequest createSinglePaymentRequest() {
        return new SinglePaymentRequest();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.CardPaymentRequestType }
     * 
     */
    public za.co.dladle.paygate.CardPaymentRequestType createCardPaymentRequestType() {
        return new CardPaymentRequestType();
    }

    /**
     * Create an instance of {@link WebPaymentRequestType }
     * 
     */
    public WebPaymentRequestType createWebPaymentRequestType() {
        return new WebPaymentRequestType();
    }

    /**
     * Create an instance of {@link TokenPaymentRequestType }
     * 
     */
    public TokenPaymentRequestType createTokenPaymentRequestType() {
        return new TokenPaymentRequestType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.BankPaymentRequestType }
     * 
     */
    public za.co.dladle.paygate.BankPaymentRequestType createBankPaymentRequestType() {
        return new BankPaymentRequestType();
    }

    /**
     * Create an instance of {@link SinglePayoutResponse }
     * 
     */
    public SinglePayoutResponse createSinglePayoutResponse() {
        return new SinglePayoutResponse();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.CardPayoutResponseType }
     * 
     */
    public za.co.dladle.paygate.CardPayoutResponseType createCardPayoutResponseType() {
        return new CardPayoutResponseType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.BankPayoutResponseType }
     * 
     */
    public za.co.dladle.paygate.BankPayoutResponseType createBankPayoutResponseType() {
        return new BankPayoutResponseType();
    }

    /**
     * Create an instance of {@link WalletPayoutResponseType }
     * 
     */
    public WalletPayoutResponseType createWalletPayoutResponseType() {
        return new WalletPayoutResponseType();
    }

    /**
     * Create an instance of {@link PingResponse }
     * 
     */
    public PingResponse createPingResponse() {
        return new PingResponse();
    }

    /**
     * Create an instance of {@link SinglePayoutRequest }
     * 
     */
    public SinglePayoutRequest createSinglePayoutRequest() {
        return new SinglePayoutRequest();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.CardPayoutRequestType }
     * 
     */
    public za.co.dladle.paygate.CardPayoutRequestType createCardPayoutRequestType() {
        return new CardPayoutRequestType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.BankPayoutRequestType }
     * 
     */
    public za.co.dladle.paygate.BankPayoutRequestType createBankPayoutRequestType() {
        return new BankPayoutRequestType();
    }

    /**
     * Create an instance of {@link WalletPayoutRequestType }
     * 
     */
    public WalletPayoutRequestType createWalletPayoutRequestType() {
        return new WalletPayoutRequestType();
    }

    /**
     * Create an instance of {@link PersonType }
     * 
     */
    public PersonType createPersonType() {
        return new PersonType();
    }

    /**
     * Create an instance of {@link ShippingDetailsType }
     * 
     */
    public ShippingDetailsType createShippingDetailsType() {
        return new ShippingDetailsType();
    }

    /**
     * Create an instance of {@link OrderType }
     * 
     */
    public OrderType createOrderType() {
        return new OrderType();
    }

    /**
     * Create an instance of {@link RiskType }
     * 
     */
    public RiskType createRiskType() {
        return new RiskType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.BillingDetailsType }
     * 
     */
    public za.co.dladle.paygate.BillingDetailsType createBillingDetailsType() {
        return new BillingDetailsType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.InstructionsResponseType }
     * 
     */
    public za.co.dladle.paygate.InstructionsResponseType createInstructionsResponseType() {
        return new InstructionsResponseType();
    }

    /**
     * Create an instance of {@link RedirectResponseType }
     * 
     */
    public RedirectResponseType createRedirectResponseType() {
        return new RedirectResponseType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.FlightLegType }
     * 
     */
    public za.co.dladle.paygate.FlightLegType createFlightLegType() {
        return new FlightLegType();
    }

    /**
     * Create an instance of {@link ThreeDSecureType }
     * 
     */
    public ThreeDSecureType createThreeDSecureType() {
        return new ThreeDSecureType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.BrowserType }
     * 
     */
    public za.co.dladle.paygate.BrowserType createBrowserType() {
        return new BrowserType();
    }

    /**
     * Create an instance of {@link PaymentType }
     * 
     */
    public PaymentType createPaymentType() {
        return new PaymentType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.AirlineBookingDetailsType }
     * 
     */
    public za.co.dladle.paygate.AirlineBookingDetailsType createAirlineBookingDetailsType() {
        return new AirlineBookingDetailsType();
    }

    /**
     * Create an instance of {@link VaultDataType }
     * 
     */
    public VaultDataType createVaultDataType() {
        return new VaultDataType();
    }

    /**
     * Create an instance of {@link OrderItemType }
     * 
     */
    public OrderItemType createOrderItemType() {
        return new OrderItemType();
    }

    /**
     * Create an instance of {@link RedirectRequestType }
     * 
     */
    public RedirectRequestType createRedirectRequestType() {
        return new RedirectRequestType();
    }

    /**
     * Create an instance of {@link PassengerType }
     * 
     */
    public PassengerType createPassengerType() {
        return new PassengerType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link za.co.dladle.paygate.AddressType }
     * 
     */
    public za.co.dladle.paygate.AddressType createAddressType() {
        return new AddressType();
    }

}
