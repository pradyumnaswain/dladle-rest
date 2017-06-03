package za.co.dladle.entity;

/**
 * Created by prady on 9/3/2016.
 */
public class UserRequest {

    private String emailId;

    private String password;

    private String deviceId;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
