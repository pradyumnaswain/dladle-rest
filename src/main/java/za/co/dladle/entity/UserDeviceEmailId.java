package za.co.dladle.entity;

/**
 * Created by prady on 6/20/2017.
 */
public class UserDeviceEmailId {
    private String deviceId;
    private String emailId;

    public UserDeviceEmailId(String deviceId, String emailId) {
        this.deviceId = deviceId;
        this.emailId = emailId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
