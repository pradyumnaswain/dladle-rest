package za.co.dladle.entity;

/**
 * Created by prady on 6/3/2017.
 */
public class PropertyInviteRequest {
    private String emailId;
    private long houseId;

    public PropertyInviteRequest() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }
}
