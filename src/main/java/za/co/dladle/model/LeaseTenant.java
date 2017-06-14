package za.co.dladle.model;

import za.co.dladle.entity.PropertyView;

import java.util.Date;

/**
 * Created by prady on 6/14/2017.
 */
public class LeaseTenant {
    private PropertyView property;
    private User landlord;
    private Date leaseStartDate;
    private Date leaseEndDate;
    private Date leaseRenewalDate;
    private Date leaseTerminateDate;

    public LeaseTenant() {
    }

    public PropertyView getProperty() {
        return property;
    }

    public void setProperty(PropertyView property) {
        this.property = property;
    }

    public User getLandlord() {
        return landlord;
    }

    public void setLandlord(User landlord) {
        this.landlord = landlord;
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
}
