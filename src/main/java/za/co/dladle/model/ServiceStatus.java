package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum ServiceStatus {
    REQUESTED("Requested");

    private final String id;

    private ServiceStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
