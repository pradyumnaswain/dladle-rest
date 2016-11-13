package za.co.dladle.entity;

/**
 * Created by prady on 11/13/2016.
 */
public class LoginRequest {
    private String emailId;

    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
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
