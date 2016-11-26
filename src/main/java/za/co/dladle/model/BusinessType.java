package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum BusinessType {
    PLUMBER(1),
    ELECTRICIAN(2),
    PAINTER(3);
    private final int id;

    private BusinessType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
