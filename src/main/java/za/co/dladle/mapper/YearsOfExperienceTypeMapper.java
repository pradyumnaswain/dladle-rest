package za.co.dladle.mapper;

import za.co.dladle.model.YearsExperience;

/**
 * Created by prady on 4/1/2017.
 */
public class YearsOfExperienceTypeMapper {
    public static Double getYearsExperience(YearsExperience yearsExperience) {
        switch (yearsExperience) {
            case ONE_YEAR:
                return 1D;
            case TWO_YEAR:
                return 2D;
            case THREE_YEARS:
                return 3D;
            case FOUR_YEARS:
                return 4D;
            case FIVE_YEARS:
                return 5D;
            case SIX_YEARS:
                return 6D;
            case SEVEN_YEARS:
                return 7D;
            case EIGHT_YEARS:
                return 8D;
            case NINE_YEARS:
                return 9D;
            case TEN_YEARS:
                return 10D;
            case ELEVEN_YEARS:
                return 11D;
            case TWELVE_YEARS:
                return 12D;
            case THIRTEEN_YEARS:
                return 13D;
            case FOURTEEN_YEARS:
                return 14D;
            case FIVETEEN_YEARS:
                return 15D;
            case SIXTEEN_YEARS:
                return 16D;
            case SEVENTEEN_YEARS:
                return 17D;
            case EIGHTTEEN_YEARS:
                return 18D;
            case TWENTY_YEARS:
                return 20D;
            default:
                return null;
            case NINETEEN_YEARS:
                return 19D;
        }
    }
}
