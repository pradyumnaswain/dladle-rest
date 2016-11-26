package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum UserType {
    TENANT(1),
    LANDLORD(2),
    VENDOR(3);

    private final int id;

    private UserType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
