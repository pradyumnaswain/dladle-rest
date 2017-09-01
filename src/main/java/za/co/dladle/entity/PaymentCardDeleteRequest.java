package za.co.dladle.entity;

/**
 * Created by prady on 9/1/2017.
 */
public class PaymentCardDeleteRequest {
    private String cardNumber;

    public PaymentCardDeleteRequest() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
