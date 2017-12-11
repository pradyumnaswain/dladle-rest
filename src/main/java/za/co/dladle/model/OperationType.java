package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum OperationType {
    KEY_GENERATION("Key Generation"),
    SERVICE_FEE("Service Fee");

    private final String id;

    OperationType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
