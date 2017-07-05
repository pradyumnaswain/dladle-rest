package za.co.dladle.entity;

/**
 * Created by prady on 7/1/2017.
 */
public class VendorResponse {
    private long vendorId;
    private Double weighted;

    public VendorResponse() {
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public Double getWeighted() {
        return weighted;
    }

    public void setWeighted(Double weighted) {
        this.weighted = weighted;
    }
}
