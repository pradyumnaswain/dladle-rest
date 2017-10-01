package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum YearsExperience {

    LESS_THAN_ONE("0.5"),
    ONE_YEAR("1"),
    BETWEEN_ONE_AND_TWO("1.5"),
    TWO_YEAR("2"),
    BETWEEN_TWO_AND_YEAR("2"),
    THREE_YEARS("3");

    private final String id;

    private YearsExperience(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
