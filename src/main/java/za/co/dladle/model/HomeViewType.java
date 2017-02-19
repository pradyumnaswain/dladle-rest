package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum HomeViewType {
    HOME_VIEW_TYPE_1(1),
    HOME_VIEW_TYPE_2(2),
    HOME_VIEW_TYPE_3(3);

    private final int id;

    private HomeViewType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
