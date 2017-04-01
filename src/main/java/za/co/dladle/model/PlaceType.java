package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum PlaceType {
    FLAT("Flat"),
    TOWN_HOUSE("Town House"),
    HOUSE("House");

    private final String id;

    private PlaceType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
