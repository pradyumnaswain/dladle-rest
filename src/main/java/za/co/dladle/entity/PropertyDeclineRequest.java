package za.co.dladle.entity;

/**
 * Created by prady on 6/3/2017.
 */
public class PropertyDeclineRequest {
    private String emailId;
    private Long houseId;

    public PropertyDeclineRequest() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
}
