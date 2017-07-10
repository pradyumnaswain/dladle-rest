package za.co.dladle.entity;

/**
 * Created by prady on 6/20/2017.
 */
public class UserDeviceEmailId {
    private String deviceId;
    private String emailId;
    private String houseId;

    public UserDeviceEmailId(String deviceId, String emailId) {
        this.deviceId = deviceId;
        this.emailId = emailId;
    }

    public UserDeviceEmailId(String deviceId, String emailId, String houseId) {
        this.deviceId = deviceId;
        this.emailId = emailId;
        this.houseId = houseId;
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

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
