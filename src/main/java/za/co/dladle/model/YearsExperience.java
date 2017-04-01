package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum YearsExperience {

    LESS_THAN_ONE("<1 Year"),
    ONE_TWO_THREE("1-2 Years"),
    THREE_TO_FIVE("3-5 Years"),
    MORE_THAN_FIVE(">5 Years");

    private final String id;

    private YearsExperience(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
