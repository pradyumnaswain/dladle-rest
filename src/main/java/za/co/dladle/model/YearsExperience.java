package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum YearsExperience {

    LESS_THAN_ONE("0.5"),
    ONE_YEAR("1"),
    BETWEEN_ONE_AND_TWO("1.5"),
    TWO_YEAR("2"),
    BETWEEN_TWO_AND_THREE("2.5"),
    THREE_YEARS("3"),
    BETWEEN_THREE_AND_FOUR("3.5"),
    FOUR_YEARS("4"),
    BETWEEN_FOUR_AND_FIVE("4.5"),
    FIVE_YEARS("5"),
    BETWEEN_FIVE_AND_SIX("5.5"),
    SIX_YEARS("6"),
    BETWEEN_SIX_AND_SEVEN("6.5"),
    SEVEN_YEARS("7"),
    BETWEEN_SEVEN_AND_EIGHT("7.5"),
    EIGHT_YEARS("8"),
    BETWEEN_EIGHT_AND_NINE("8.5"),
    NINE_YEARS("9"),
    BETWEEN_NINE_AND_TEN("9.5"),
    TEN_YEARS("10"),
    MORE_THEN_TEN_YEARS("11");

    private final String id;

    private YearsExperience(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
