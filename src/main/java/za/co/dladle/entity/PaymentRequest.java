package za.co.dladle.entity;

import za.co.dladle.model.OperationType;
import za.co.dladle.model.TransactionType;

/**
 * Created by prady on 9/5/2017.
 */
public class PaymentRequest {
    private OperationType operationType;
    private String cardNumber;
    private String cvvNumber;
    private Integer amount;

    public PaymentRequest() {
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
