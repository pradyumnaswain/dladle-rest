package za.co.dladle.mapper;

import za.co.dladle.model.NotificationType;
import za.co.dladle.model.UserType;

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
            default:
                return null;
        }
    }
}
