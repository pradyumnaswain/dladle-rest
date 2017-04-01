package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum ServiceType {
    PLUMBER("Plumber"),
    ELECTRICIAN("Electrician"),
    PAINTER("Painter");

    private final String id;

    private ServiceType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
