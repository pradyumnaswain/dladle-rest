package za.co.dladle.entity;

/**
 * Created by prady on 7/2/2017.
 */
public class VendorEstimate {
    private Double feeStartRange;
    private Double feeEndRange;
    private long serviceId;

    public VendorEstimate() {
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

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }
}
