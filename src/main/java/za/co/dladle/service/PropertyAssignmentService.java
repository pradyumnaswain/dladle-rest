package za.co.dladle.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import za.co.dladle.entity.*;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.NotificationType;
import za.co.dladle.session.UserSession;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 6/18/2017.
 */
public class PropertyAssignmentService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private AndroidPushNotificationsService pushNotificationsService;

    @Autowired
    private PushNotificationService notificationService;

    @Autowired
    private NotificationServiceSendGridImpl emailService;

    @Transactional
    public void assignPropertyToTenant(PropertyAssignmentRequest propertyAssignmentRequest) throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        Map<String, Object> map = new HashMap<>();
        if (userSession.getUser().getUserType().eqTENANT()) {
            map.put("landlordEmailId", propertyAssignmentRequest.getEmailId());
            String landlordSql = "SELECT landlord.id landord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=:landlordEmailId";

            try {
                Long landlordId = this.parameterJdbcTemplate.queryForObject(landlordSql, map, Long.class);
                map.put("tenantEmailId", userSession.getUser().getEmailId());
                String tenantSql = "SELECT tenant.id tenant_id FROM user_dladle INNER JOIN tenant ON user_dladle.id = tenant.user_id WHERE emailid=:tenantEmailId";
                Long tenantId = this.parameterJdbcTemplate.queryForObject(tenantSql, map, Long.class);

                map.put("tenantId", tenantId);
                map.put("houseId", propertyAssignmentRequest.getHouseId());

                String sql = "UPDATE tenant SET house_id=:houseId WHERE tenant.id=:tenantId";

                parameterJdbcTemplate.update(sql, map);

                //Create/Update Lease
                leaseService.createOrUpdateLease(propertyAssignmentRequest.getHouseId(), tenantId);

                notificationService.actionNotifications(landlordId, tenantId);

            } catch (Exception e) {
                throw new Exception("Landlord doesn't exist for the given email Id");
            }
        } else if (userSession.getUser().getUserType().eqLANDLORD()) {
            map.put("tenantEmailId", propertyAssignmentRequest.getEmailId());
            String tenantSql = "SELECT tenant.id landord_id FROM user_dladle INNER JOIN tenant ON user_dladle.id = tenant.user_id WHERE emailid=:tenantEmailId";

            try {
                Long tenantId = this.parameterJdbcTemplate.queryForObject(tenantSql, map, Long.class);
                map.put("landlordEmailId", userSession.getUser().getEmailId());
                String landlordSql = "SELECT landlord.id landord_id FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id WHERE emailid=:landlordEmailId";
                Long landlordId = this.parameterJdbcTemplate.queryForObject(landlordSql, map, Long.class);

                map.put("tenantId", tenantId);
                map.put("houseId", propertyAssignmentRequest.getHouseId());

                String sql = "UPDATE tenant SET house_id=:houseId WHERE tenant.id=:tenantId";

                parameterJdbcTemplate.update(sql, map);

                //Create/Update Lease
                leaseService.createOrUpdateLease(propertyAssignmentRequest.getHouseId(), tenantId);

                notificationService.actionNotifications(tenantId, landlordId);

            } catch (Exception e) {
                throw new Exception("Landlord doesn't exist for the given email Id");
            }

        } else {
            throw new Exception("Not authorised to use this API");
        }
    }


    public void inviteTenant(PropertyInviteRequest propertyInviteRequest) throws Exception {

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Map<String, Object> map = new HashMap<>();
        map.put("emailId", propertyInviteRequest.getEmailId());
        String sql = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";
        String deviceId = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);

        //save notification
        NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                propertyInviteRequest.getEmailId(),
                "New Property Request",
                "Please accept this property invitation",
                "landlordEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + propertyInviteRequest.getHouseId(),
                "", String.valueOf(propertyInviteRequest.getHouseId()), NotificationType.LANDLORD_REQUEST_TENANT);
        notificationService.saveNotification(notifications);

        //Send Email
        emailService.sendPropertyInviteMail(propertyInviteRequest.getEmailId());

        //Send Push Notification
        if (deviceId != null) {
            JSONObject body = new JSONObject();
            body.put("to", deviceId);
            body.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("body", "Please accept this property invitation");
            notification.put("title", "New Property Request");

            JSONObject data = new JSONObject();
            data.put("landlordEmailId", userSession.getUser().getEmailId());
            data.put("houseId", propertyInviteRequest.getHouseId());

            body.put("notification", notification);
            body.put("data", data);

            pushNotificationsService.sendNotification(body);
        } else {
            System.out.println("Device Id can't be null");
        }
    }

    public void requestLandlord(PropertyRequest propertyRequest) throws UserNotFoundException, JSONException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Map<String, Object> map = new HashMap<>();
        map.put("emailId", propertyRequest.getEmailId());
        String sql = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";
        String deviceId = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);

        //save notification
        NotificationView notifications = new NotificationView(
                userSession.getUser().getEmailId(),
                propertyRequest.getEmailId(),
                "Property Request from Tenant",
                "Please accept this property Request",
                "tenantEmailId:" + userSession.getUser().getEmailId(),
                "", null, NotificationType.TENANT_REQUEST_LANDLORD);
        notificationService.saveNotification(notifications);

        //Send Email
        emailService.sendPropertyRequesteMail(propertyRequest.getEmailId());

        //Send Push Notification
        if (deviceId != null) {
            JSONObject body = new JSONObject();
            body.put("to", deviceId);
            body.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("body", "Please accept Property request from Tenant");
            notification.put("title", "New Tenant Request");

            JSONObject data = new JSONObject();
            data.put("landlordEmailId", userSession.getUser().getEmailId());
            body.put("notification", notification);
            body.put("data", data);
            pushNotificationsService.sendNotification(body);
        } else {
            System.out.println("Device Id can't be null");
        }
    }

    public void declineProperty(PropertyDeclineRequest propertyDeclineRequest) throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        if (userSession.getUser().getUserType().eqTENANT()) {
            Map<String, Object> map = new HashMap<>();
            map.put("landlordEmailId", propertyDeclineRequest.getEmailId());
            String sql = "SELECT device_id FROM user_dladle WHERE emailid=:landlordEmailId";
            String deviceId = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);
            //Send Email
            //save notification
            NotificationView notifications = new NotificationView(
                    userSession.getUser().getEmailId(),
                    propertyDeclineRequest.getEmailId(),
                    "Property Decline from Tenant",
                    "Tenant Declined the Property Invitation ",
                    "tenantEmailId:" + userSession.getUser().getEmailId(),
                    "", propertyDeclineRequest.getHouseId().toString(), NotificationType.TENANT_REJECTS_PROPERTY_INVITATION);
            notificationService.saveNotification(notifications);
            emailService.sendPropertyDeclineMail(propertyDeclineRequest.getEmailId());

            //Send Push Notification
            if (deviceId != null) {
                JSONObject body = new JSONObject();
                body.put("to", deviceId);
                body.put("priority", "high");

                JSONObject notification = new JSONObject();
                notification.put("title", "Property Decline from Tenant");
                notification.put("body", "Tenant Declined the Property Invitation");

                JSONObject data = new JSONObject();
                body.put("notification", notification);
                body.put("data", data);
                pushNotificationsService.sendNotification(body);
            } else {
                System.out.println("Device Id can't be null");
            }

        } else if (userSession.getUser().getUserType().eqLANDLORD()) {

            Map<String, Object> map = new HashMap<>();
            map.put("tenantEmailId", propertyDeclineRequest.getEmailId());
            String sql = "SELECT device_id FROM user_dladle WHERE emailid=:tenantEmailId";
            String deviceId = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);
            //Send Email
            //save notification
            NotificationView notifications = new NotificationView(
                    userSession.getUser().getEmailId(),
                    propertyDeclineRequest.getEmailId(),
                    "Property Request Decline fromLandlord",
                    "Landlord Declined the Property Request ",
                    "tenantEmailId:" + userSession.getUser().getEmailId(),
                    "", null, NotificationType.LANDLORD_REJECTS_PROPERTY_INVITATION);
            notificationService.saveNotification(notifications);
            emailService.sendPropertyDeclineMail(propertyDeclineRequest.getEmailId());

            //Send Push Notification
            if (deviceId != null) {
                JSONObject body = new JSONObject();
                body.put("to", deviceId);
                body.put("priority", "high");

                JSONObject notification = new JSONObject();
                notification.put("title", "Property Request Decline fromLandlord");
                notification.put("body", "Landlord Declined the Property Request");

                JSONObject data = new JSONObject();
                body.put("notification", notification);
                body.put("data", data);
                pushNotificationsService.sendNotification(body);
            } else {
                System.out.println("Device Id can't be null");
            }
        } else {
            throw new Exception("Not authorised to use this API");
        }
    }
}
