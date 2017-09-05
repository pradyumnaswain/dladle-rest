package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum TransactionType {
    CREDIT("Credit"),
    DEBIT("Debit");

    private final String id;

    private TransactionType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
