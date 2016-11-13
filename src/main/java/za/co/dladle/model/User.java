package za.co.dladle.model;

/**
 * Created by prady on 9/3/2016.
 */
public class User {

    private String emailId;

    private String password;

    public User() {
    }

    public User(String emailId) {
        this.emailId = emailId;
    }

    public User(String emailId, String password) {
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
