package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum ServiceType {
    PLUMBER(1),
    ELECTRICIAN(2),
    PAINTER(3);
    private final int id;

    private ServiceType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
