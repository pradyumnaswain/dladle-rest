package za.co.dladle.mapper;

import za.co.dladle.model.YearsExperience;

/**
 * Created by prady on 4/1/2017.
 */
public class YearsOfExperienceTypeMapper {
    public static Integer getYearsExperience(YearsExperience yearsExperience) {
        switch (yearsExperience) {
            case LESS_THAN_ONE:
                return 1;
            case ONE_TWO_THREE:
                return 2;
            case THREE_TO_FIVE:
                return 3;
            case MORE_THAN_FIVE:
                return 4;
            default:
                return null;
        }
    }
}
