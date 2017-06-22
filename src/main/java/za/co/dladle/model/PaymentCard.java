package za.co.dladle.model;

/**
 * Created by prady on 6/22/2017.
 */
public class PaymentCard {
    private String nameOnCard;
    private String cardNumber;
    private String expiryDate;
    private String cvvNumber;
    private String cardType;

    public PaymentCard(String nameOnCard, String cardNumber, String expiryDate, String cvvNumber, String cardType) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvvNumber = cvvNumber;
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

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
