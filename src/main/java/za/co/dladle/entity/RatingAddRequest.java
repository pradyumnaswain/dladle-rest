package za.co.dladle.entity;

/**
 * Created by prady on 4/16/2017.
 */
public class RatingAddRequest {
    private Double rate;
    private String comment;
    private String ratedUserEmailId;

    public RatingAddRequest() {
    }

    public RatingAddRequest(Double rate, String comment, String ratedUserEmailId) {
        this.rate = rate;
        this.comment = comment;
        this.ratedUserEmailId = ratedUserEmailId;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRatedUserEmailId() {
        return ratedUserEmailId;
    }

    public void setRatedUserEmailId(String ratedUserEmailId) {
        this.ratedUserEmailId = ratedUserEmailId;
    }
}
