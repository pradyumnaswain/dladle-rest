package za.co.dladle.entity;

/**
 * Created by prady on 7/1/2017.
 */
public class Vendor {
    private Long vendorId;
    private String experience;
    private Double proximity;
    private Double rating;
    private Double feeStartRange;
    private Double feeEndRange;
    private Long mRequestTime;

    // These values to be computed after initialization.
    private Double mWeightedRating;
    private Double mAverageWeight;
    public Vendor() {
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Double getProximity() {
        return proximity;
    }

    public void setProximity(Double proximity) {
        this.proximity = proximity;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getFeeStartRange() {
        return feeStartRange;
    }

    public void setFeeStartRange(Double feeStartRange) {
        this.feeStartRange = feeStartRange;
    }

    public Double getFeeEndRange() {
        return feeEndRange;
    }

    public void setFeeEndRange(Double feeEndRange) {
        this.feeEndRange = feeEndRange;
    }

    public Long getmRequestTime() {
        return mRequestTime;
    }

    public void setmRequestTime(Long mRequestTime) {
        this.mRequestTime = mRequestTime;
    }

    public Double getmWeightedRating() {
        return mWeightedRating;
    }

    public void setmWeightedRating(Double mWeightedRating) {
        this.mWeightedRating = mWeightedRating;
    }

    public Double getmAverageWeight() {
        return mAverageWeight;
    }

    public void setmAverageWeight(Double mAverageWeight) {
        this.mAverageWeight = mAverageWeight;
    }
}
