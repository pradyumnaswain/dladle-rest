package za.co.dladle.entity;

/**
 * Created by prady on 11/19/2017.
 */
public class AcceptRequest {
    private Long serviceId;
    private String vendorEmailId;


    public AcceptRequest() {
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getVendorEmailId() {
        return vendorEmailId;
    }

    public void setVendorEmailId(String vendorEmailId) {
        this.vendorEmailId = vendorEmailId;
    }
}
