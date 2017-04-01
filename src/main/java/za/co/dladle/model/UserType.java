package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum UserType {
    TENANT("Tenant"),
    LANDLORD("Landlord"),
    VENDOR("Vendor");

    private final String id;

    private UserType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
