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
            case TENANT_REQUEST_LANDLORD:
                return 2;
            case LEASE_TERMINATE:
                return 3;
            case LEASE_RENEWAL:
                return 4;
            case TENANT_ACCEPTS_PROPERTY_INVITATION:
                return 5;
            case TENANT_REJECTS_PROPERTY_INVITATION:
                return 6;
            case LANDLORD_ACCEPTS_PROPERTY_INVITATION:
                return 7;
            case LANDLORD_REJECTS_PROPERTY_INVITATION:
                return 8;
            default:
                return null;
        }
    }
}
