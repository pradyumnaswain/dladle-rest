package za.co.dladle.entity;

import java.util.List;

/**
 * Created by prady on 7/1/2017.
 */
public class VendorRequest {
    private List<Vendor> vendors;
    private boolean emergency;

    public VendorRequest() {
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }
}
