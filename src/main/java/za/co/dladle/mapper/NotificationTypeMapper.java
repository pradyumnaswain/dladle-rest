package za.co.dladle.mapper;

import za.co.dladle.model.NotificationType;

/**
 * Created by prady on 4/1/2017.
 */
public class NotificationTypeMapper {
    public static Integer getNotificationType(NotificationType notificationType) {
        switch (notificationType) {
            case LANDLORD_REQUEST_TENANT:
                return 1;
            case TENANT_ACCEPTS_PROPERTY_INVITATION:
                return 2;
            case TENANT_REJECTS_PROPERTY_INVITATION:
                return 3;
            case TENANT_REQUEST_LANDLORD:
                return 4;
            case LANDLORD_ACCEPTS_PROPERTY_INVITATION:
                return 5;
            case LANDLORD_REJECTS_PROPERTY_INVITATION:
                return 6;
            case LEASE_TERMINATE_REQUEST_LANDLORD:
                return 7;
            case LEASE_TERMINATE_TENANT_ACCEPT:
                return 8;
            case LEASE_TERMINATE_TENANT_REJECT:
                return 9;
            case LEASE_TERMINATE_REQUEST_TENANT:
                return 10;
            case LEASE_TERMINATE_LANDLORD_ACCEPT:
                return 11;
            case LEASE_TERMINATE_LANDLORD_REJECT:
                return 12;
            case LEASE_REMOVES_TENANT:
                return 13;
            case LEASE_LEAVES_TENANT:
                return 14;
            case RATE_TENANT:
                return 15;
            case RATE_LANDLORD:
                return 16;
            case RATE_VENDOR:
                return 17;
            case SERVICE_REQUEST:
                return 18;
            case SERVICE_REQUEST_ACCEPT:
                return 19;
            case SERVICE_REQUEST_REJECTED:
                return 20;
            case SERVICE_FINAL_PRICE_ESTIMATED:
                return 21;
            case SERVICE_ESTIMATE_ACCEPTED:
                return 22;
            case SERVICE_ESTIMATE_REJECTED:
                return 23;
            default:
                return null;
        }
    }
}
