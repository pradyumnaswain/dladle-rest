package za.co.dladle.mapper;

import za.co.dladle.model.UserType;

/**
 * Created by prady on 4/1/2017.
 */
public class UserTypeMapper {
    public static Integer getUserType(UserType userType) {
        switch (userType) {
            case TENANT:
                return 1;
            case VENDOR:
                return 3;
            case LANDLORD:
                return 2;
            default:
                return null;
        }
    }

    public static UserType getUserType(int userTypeId) {
        switch (userTypeId) {
            case 1:
                return UserType.TENANT;
            case 3:
                return UserType.VENDOR;
            case 2:
                return UserType.LANDLORD;
            default:
                return null;
        }
    }
}
