package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum HomeViewType {
    HOME_VIEW_TYPE_1("Home View Type 1"),
    HOME_VIEW_TYPE_2("Home View Type 2"),
    HOME_VIEW_TYPE_3("Home View Type 3");

    private final String id;

    private HomeViewType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
