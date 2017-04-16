package za.co.dladle.entity;

/**
 * Created by prady on 4/16/2017.
 */
public class RatingView {
    private Double rate;
    private Integer count;

    public RatingView() {
    }

    public RatingView(Double rate, Integer count) {
        this.rate = rate;
        this.count = count;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
