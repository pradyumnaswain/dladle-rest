package za.co.dladle.entity;

/**
 * Created by prady on 7/3/2017.
 */
public class VendorAtWorkView {
    private long vendorId;
    private String currentLocationLatitude;
    private String currentLocationLongitude;
    private String experience;
    private Double proximity;
    private Double rating;

    public VendorAtWorkView() {
    }

    public VendorAtWorkView(long vendorId, String currentLocationLatitude, String currentLocationLongitude, String experience) {
        this.vendorId = vendorId;
        this.currentLocationLatitude = currentLocationLatitude;
        this.currentLocationLongitude = currentLocationLongitude;
        this.experience = experience;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public String getCurrentLocationLatitude() {
        return currentLocationLatitude;
    }

    public void setCurrentLocationLatitude(String currentLocationLatitude) {
        this.currentLocationLatitude = currentLocationLatitude;
    }

    public String getCurrentLocationLongitude() {
        return currentLocationLongitude;
    }

    public void setCurrentLocationLongitude(String currentLocationLongitude) {
        this.currentLocationLongitude = currentLocationLongitude;
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
