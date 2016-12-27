package za.co.dladle.entity;

import za.co.dladle.model.UserType;

/**
 * Created by prady on 9/3/2016.
 */
public class UserRequest {

    private String emailId;

    private String password;

    public UserRequest() {
    }

    public UserRequest(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
