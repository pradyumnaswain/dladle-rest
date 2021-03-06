package za.co.dladle.entity;

/**
 * Created by prady on 7/1/2017.
 */
public class VendorAccountStatus {
    private boolean accountSet;
    private boolean accountVerified;
    private Integer serviceType;

    public VendorAccountStatus() {
    }

    public VendorAccountStatus(boolean accountSet, boolean accountVerified, Integer serviceType) {
        this.accountSet = accountSet;
        this.accountVerified = accountVerified;
        this.serviceType = serviceType;
    }

    public boolean isAccountSet() {
        return accountSet;
    }

    public void setAccountSet(boolean accountSet) {
        this.accountSet = accountSet;
    }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }
}
