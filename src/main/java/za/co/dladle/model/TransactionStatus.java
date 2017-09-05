package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum TransactionStatus {
    SUCCESS("Success"),
    FAIL("Fail"),
    PENDING("Pending"),
    UNKNOWN("Unknown");

    private final String id;

    private TransactionStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
