package za.co.dladle.entity;

/**
 * Created by prady on 6/27/2017.
 */
public class VendorOnWork {
    private String currentLocationLatitude;

    private String currentLocationLongitude;

    public VendorOnWork() {
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
}
