package za.co.dladle.model;

/**
 * Created by prady on 11/20/2016.
 */
public class UserRegisterRequest {
    private String emailId;

    private String name;

    private String password;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String emailId, String name, String password) {
        this.emailId = emailId;
        this.name = name;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
