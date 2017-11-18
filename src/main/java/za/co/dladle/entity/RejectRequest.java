package za.co.dladle.entity;

import za.co.dladle.model.RejectionReason;

/**
 * Created by prady on 11/19/2017.
 */
public class RejectRequest {
    private Long serviceId;

    private String vendorEmailId;

    private RejectionReason rejectionReason;

    public RejectRequest() {
    }

    public String getVendorEmailId() {
        return vendorEmailId;
    }

    public void setVendorEmailId(String vendorEmailId) {
        this.vendorEmailId = vendorEmailId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(RejectionReason rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
