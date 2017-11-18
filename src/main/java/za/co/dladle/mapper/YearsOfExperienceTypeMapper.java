package za.co.dladle.mapper;

import za.co.dladle.model.YearsExperience;

/**
 * Created by prady on 4/1/2017.
 */
public class YearsOfExperienceTypeMapper {
    public static Double getYearsExperience(YearsExperience yearsExperience) {
        switch (yearsExperience) {
            case LESS_THAN_ONE:
                return 0.5;
            case ONE_YEAR:
                return 1D;
            case BETWEEN_ONE_AND_TWO:
                return 1.5;
            case TWO_YEAR:
                return 2D;
            case BETWEEN_TWO_AND_THREE:
                return 2.5;
            case THREE_YEARS:
                return 3D;
            case BETWEEN_THREE_AND_FOUR:
                return 3.5;
            case SIX_YEARS:
                return 6D;
            case TEN_YEARS:
                return 10D;
            case FIVE_YEARS:
                return 5D;
            case FOUR_YEARS:
                return 4D;
            case NINE_YEARS:
                return 9D;
            case EIGHT_YEARS:
                return 8D;
            case SEVEN_YEARS:
                return 7D;
            case MORE_THEN_TEN_YEARS:
                return 11D;
            case BETWEEN_FIVE_AND_SIX:
                return 5.5;
            case BETWEEN_NINE_AND_TEN:
                return 9.5;
            case BETWEEN_FOUR_AND_FIVE:
                return 4.5;
            case BETWEEN_SIX_AND_SEVEN:
                return 6.5;
            case BETWEEN_EIGHT_AND_NINE:
                return 8.5;
            case BETWEEN_SEVEN_AND_EIGHT:
                return 7.5;
            default:
                return null;
        }
    }
}
