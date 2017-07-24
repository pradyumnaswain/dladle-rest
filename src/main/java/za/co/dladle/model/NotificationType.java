package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum NotificationType {
    LANDLORD_REQUEST_TENANT("Landlord Request Tenant to Property"),
    TENANT_ACCEPTS_PROPERTY_INVITATION("Tenant Accepts Property Invitation"),
    TENANT_REJECTS_PROPERTY_INVITATION("Tenant Rejects Property Invitation"),
    TENANT_REQUEST_LANDLORD("Tenant Request Landlord for Property"),
    LANDLORD_ACCEPTS_PROPERTY_INVITATION("Landlord Accepts Property Request"),
    LANDLORD_REJECTS_PROPERTY_INVITATION("Landlord Rejects Property Request"),
    LEASE_TERMINATE_REQUEST_LANDLORD("Lease Terminate Request from Landlord to Tenant"),
    LEASE_TERMINATE_TENANT_ACCEPT("Tenant Accepts Lease Termination"),
    LEASE_TERMINATE_TENANT_REJECT("Tenant Rejects Lease Termination"),
    LEASE_TERMINATE_REQUEST_TENANT("Lease Terminate Request from Tenant to Landlord"),
    LEASE_TERMINATE_LANDLORD_ACCEPT("Landlord Accepts Lease Termination"),
    LEASE_TERMINATE_LANDLORD_REJECT("Landlord Rejects Lease Termination"),
    LEASE_REMOVES_TENANT("Landlord Removes Tenant from Lease"),
    LEASE_LEAVES_TENANT("Tenant Leaves Lease"),
    RATE_TENANT("Rate your Tenant"),
    RATE_LANDLORD("Rate your Landlord"),
    RATE_VENDOR("Rate Vendor"),
    SERVICE_REQUEST("Service Request");


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

}
