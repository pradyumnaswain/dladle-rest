package za.co.dladle.model;

/**
 * Created by prady on 6/22/2017.
 */
public class PaymentCard {
    private String nameOnCard;
    private String cardNumber;
    private String expiryDate;
    private String cardType;

    public PaymentCard(String nameOnCard, String cardNumber, String cardType) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    public PaymentCard() {
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
