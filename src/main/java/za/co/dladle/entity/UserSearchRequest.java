package za.co.dladle.entity;

import za.co.dladle.model.UserType;

/**
 * Created by prady on 6/3/2017.
 */
public class UserSearchRequest {
    private UserType userType;
    private String name;

    public UserSearchRequest() {
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
