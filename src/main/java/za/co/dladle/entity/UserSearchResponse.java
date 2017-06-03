package za.co.dladle.entity;

/**
 * Created by prady on 6/3/2017.
 */
public class UserSearchResponse {
    private String name;
    private String emailId;

    public UserSearchResponse() {
    }

    public UserSearchResponse(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
