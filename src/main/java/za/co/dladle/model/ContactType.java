package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum ContactType {
    HOSPITAL("Hospital"),
    POLICE_STATION("Police Station"),
    FIRE_STATION("Fire Station");

    private final String id;

    private ContactType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
