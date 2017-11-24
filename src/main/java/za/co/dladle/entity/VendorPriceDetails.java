package za.co.dladle.entity;

/**
 * Created by prady on 11/24/2017.
 */
public class VendorPriceDetails {
    private String feeStartRange;
    private String feeEndRange;

    public VendorPriceDetails(String feeStartRange, String feeEndRange) {
        this.feeStartRange = feeStartRange;
        this.feeEndRange = feeEndRange;
    }

    public String getFeeStartRange() {
        return feeStartRange;
    }

    public void setFeeStartRange(String feeStartRange) {
        this.feeStartRange = feeStartRange;
    }

    public String getFeeEndRange() {
        return feeEndRange;
    }

    public void setFeeEndRange(String feeEndRange) {
        this.feeEndRange = feeEndRange;
    }
}
