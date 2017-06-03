package za.co.dladle.entity;

/**
 * Created by prady on 6/3/2017.
 */
public class PropertyInviteRequest {
    private String emailId;
    private String houseId;

    public PropertyInviteRequest() {
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
