package za.co.dladle.entity;

import za.co.dladle.model.OperationType;
import za.co.dladle.model.TransactionType;

/**
 * Created by prady on 9/5/2017.
 */
public class PaymentRequest {
    private OperationType operationType;
    private Long cardId;
    private String cvvNumber;
    private Double amount;

    public PaymentRequest() {
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
