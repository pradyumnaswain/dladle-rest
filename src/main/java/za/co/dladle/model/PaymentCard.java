package za.co.dladle.model;

/**
 * Created by prady on 6/22/2017.
 */
public class PaymentCard {
    private Long cardId;
    private String nameOnCard;
    private String cardNumber;
    private String expiryDate;
    private String cardType;

    public PaymentCard(Long cardId, String nameOnCard, String cardNumber, String expiryDate, String cardType) {
        this.cardId = cardId;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cardType = cardType;
    }

    public PaymentCard() {
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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
