package za.co.dladle.model;

import za.co.dladle.entity.TenantLeaseView;

import java.util.Date;
import java.util.List;

/**
 * Created by prady on 6/14/2017.
 */
public class LeaseLandlord {
    private long leaseId;
    private Date leaseStartDate;
    private Date leaseEndDate;
    private Date leaseRenewalDate;
    private Date leaseTerminateDate;
    private List<TenantLeaseView> tenantList;

    public LeaseLandlord() {
    }

    public long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(long leaseId) {
        this.leaseId = leaseId;
    }

    public Date getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(Date leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public Date getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(Date leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public Date getLeaseRenewalDate() {
        return leaseRenewalDate;
    }

    public void setLeaseRenewalDate(Date leaseRenewalDate) {
        this.leaseRenewalDate = leaseRenewalDate;
    }

    public Date getLeaseTerminateDate() {
        return leaseTerminateDate;
    }

    public void setLeaseTerminateDate(Date leaseTerminateDate) {
        this.leaseTerminateDate = leaseTerminateDate;
    }

    public List<TenantLeaseView> getTenantList() {
        return tenantList;
    }

    public void setTenantList(List<TenantLeaseView> tenantList) {
        this.tenantList = tenantList;
    }
}
