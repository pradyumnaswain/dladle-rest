package za.co.dladle.entity;

/**
 * Created by prady on 9/30/2017.
 */
public class UserDeleteRequest {
    private String password;

    public UserDeleteRequest() {
    }

    public UserDeleteRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
