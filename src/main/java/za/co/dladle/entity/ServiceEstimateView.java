package za.co.dladle.entity;

public class ServiceEstimateView {
    private String feeStartRange;
    private String feeEndRange;

    public ServiceEstimateView() {
    }

    public ServiceEstimateView(String feeStartRange, String feeEndRange) {
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
