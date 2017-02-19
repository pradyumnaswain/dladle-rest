package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum YearsExperience {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    MORE_THAN_10(11);

    private final int id;

    private YearsExperience(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
