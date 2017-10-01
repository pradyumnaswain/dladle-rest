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
            case BETWEEN_TWO_AND_YEAR:
                return 2.5;
            case THREE_YEARS:
                return 3D;
            default:
                return null;
        }
    }
}
