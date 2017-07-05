package za.co.dladle.entity;

/**
 * Created by prady on 7/3/2017.
 */
public class VendorAtWorkView {
    private long vendorId;
    private String currentLocation;
    private String experience;
    private Double proximity;
    private Double rating;

    public VendorAtWorkView() {
    }

    public VendorAtWorkView(long vendorId, String currentLocation, String experience, Double rating) {
        this.vendorId = vendorId;
        this.currentLocation = currentLocation;
        this.experience = experience;
        this.rating = rating;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
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
}
