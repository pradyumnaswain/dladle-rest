package za.co.dladle.entity;

/**
 * Created by prady on 4/16/2017.
 */
public class RatingViewDetails {
    private Double rate;
    private String comment;
    private String name;

    public RatingViewDetails() {
    }

    public RatingViewDetails(Double rate, String comment, String name) {
        this.rate = rate;
        this.comment = comment;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
