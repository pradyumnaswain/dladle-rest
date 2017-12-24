package za.co.dladle.entity;

public class Transaction {
    private String referenceNo;
    private Double amount;
    private String transactionType;
    private String operationType;
    private String transactionStatus;
    private String transactionTime;
    private String cardNumber;

    public Transaction(String referenceNo, Double amount, String transactionType, String operationType, String transactionStatus, String transactionTime, String cardNumber) {
        this.referenceNo = referenceNo;
        this.amount = amount;
        this.transactionType = transactionType;
        this.operationType = operationType;
        this.transactionStatus = transactionStatus;
        this.transactionTime = transactionTime;
        this.cardNumber = cardNumber;
    }

    public Transaction() {
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
