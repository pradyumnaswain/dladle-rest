package za.co.dladle.entity;

/**
 * Created by prady on 6/27/2017.
 */
public class VendorWorkStatus {
    private boolean currentWorkStatus;
    private String workFrom;
    private String workTo;

    public VendorWorkStatus() {
    }

    public VendorWorkStatus(boolean currentWorkStatus, String workFrom, String workTo) {
        this.currentWorkStatus = currentWorkStatus;
        this.workFrom = workFrom;
        this.workTo = workTo;
    }

    public boolean isCurrentWorkStatus() {
        return currentWorkStatus;
    }

    public void setCurrentWorkStatus(boolean currentWorkStatus) {
        this.currentWorkStatus = currentWorkStatus;
    }

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public String getWorkTo() {
        return workTo;
    }

    public void setWorkTo(String workTo) {
        this.workTo = workTo;
    }
}
