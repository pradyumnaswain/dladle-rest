package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum PlaceType {
    FLAT(1),
    TOWN_HOUSE(2),
    HOUSE(3);

    private final int id;

    private PlaceType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
