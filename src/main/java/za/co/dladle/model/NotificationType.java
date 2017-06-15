package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum NotificationType {
    LANDLORD_REQUEST_TENANT("Landlord Request Tenant to Property"),
    TENANT_REQUEST_LANDLORD("Tenant Request Landlord for Property"),
    LEASE_TERMINATE("Lease Terminate Request"),
    LEASE_RENEWAL("Lease Renewal Request");

    private final String id;

    private NotificationType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public Boolean eqLANDLORD_REQUEST_TENANT() {
        return getId().equals(NotificationType.LANDLORD_REQUEST_TENANT.getId());
    }

    public Boolean eqTENANT_REQUEST_LANDLORD() {
        return getId().equals(NotificationType.TENANT_REQUEST_LANDLORD.getId());
    }

    public Boolean eqLEASE_TERMINATE() {
        return getId().equals(NotificationType.LEASE_TERMINATE.getId());
    }

    public Boolean eqLEASE_RENEWAL() {
        return getId().equals(NotificationType.LEASE_RENEWAL.getId());
    }
}
