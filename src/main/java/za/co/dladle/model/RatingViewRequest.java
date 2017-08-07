package za.co.dladle.model;

/**
 * Created by prady on 8/7/2017.
 */
public class RatingViewRequest {
    private String userEmailId;

    public RatingViewRequest() {
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }
}
