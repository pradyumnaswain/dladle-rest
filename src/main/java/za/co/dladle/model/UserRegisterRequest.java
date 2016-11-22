package za.co.dladle.model;

/**
 * Created by prady on 11/20/2016.
 */
public class UserRegisterRequest {
    private String emailId;

    private String name;

    private String password;

    private Integer user_type;

    private boolean verified;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String emailId, String name, String password, Integer user_type, boolean verified) {
        this.emailId = emailId;
        this.name = name;
        this.password = password;
        this.user_type = user_type;
        this.verified = verified;
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

    public void setName(String name) { this.name = name; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
