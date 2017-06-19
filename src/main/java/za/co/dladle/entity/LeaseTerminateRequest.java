package za.co.dladle.entity;

/**
 * Created by prady on 6/19/2017.
 */
public class LeaseTerminateRequest {
    private long houseId;
    private long leaseId;

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(long leaseId) {
        this.leaseId = leaseId;
    }

    public LeaseTerminateRequest() {
    }
}
