package za.co.dladle.model;

/**
 * Created by 310252258 on 19/02/2017.
 */
public enum YearsExperience {

    ONE_YEAR("01"),
    TWO_YEAR("02"),
    THREE_YEARS("03"),
    FOUR_YEARS("04"),
    FIVE_YEARS("05"),
    SIX_YEARS("06"),
    SEVEN_YEARS("07"),
    EIGHT_YEARS("08"),
    NINE_YEARS("09"),
    TEN_YEARS("10"),
    ELEVEN_YEARS("11"),
    TWELVE_YEARS("12"),
    THIRTEEN_YEARS("13"),
    FOURTEEN_YEARS("14"),
    FIVETEEN_YEARS("15"),
    SIXTEEN_YEARS("16"),
    SEVENTEEN_YEARS("17"),
    EIGHTTEEN_YEARS("18"),
    NINETEEN_YEARS("19"),
    TWENTY_YEARS("20");

    private final String id;

    private YearsExperience(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
