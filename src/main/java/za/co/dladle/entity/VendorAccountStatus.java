package za.co.dladle.entity;

/**
 * Created by prady on 7/1/2017.
 */
public class VendorAccountStatus {
    private boolean accountSet;
    private boolean accountVerified;

    public VendorAccountStatus() {
    }

    public VendorAccountStatus(boolean accountSet, boolean accountVerified) {
        this.accountSet = accountSet;
        this.accountVerified = accountVerified;
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
}
