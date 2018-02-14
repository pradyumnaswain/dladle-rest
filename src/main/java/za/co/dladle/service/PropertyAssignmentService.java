package za.co.dladle.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.apiutil.NotificationConstants;
import za.co.dladle.entity.*;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.NotificationType;
import za.co.dladle.model.User;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.thirdparty.email.EmailServiceZohoMailImpl;
import za.co.dladle.thirdparty.push.AndroidPushNotificationsService;
import za.co.dladle.thirdparty.email.EmailServiceSendGridImpl;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 6/18/2017.
 */
@Service
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
    private UserUtility userUtility;

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

                String sqlDevice = "SELECT device_id FROM user_dladle WHERE emailid=:landlordEmailId";
                String deviceId = this.parameterJdbcTemplate.queryForObject(sqlDevice, map, String.class);

                NotificationView notifications = new NotificationView(
                        userSession.getUser().getEmailId(),
                        propertyAssignmentRequest.getEmailId(),
                        NotificationConstants.PROPERTY_ACCEPTED_TITLE,
                        NotificationConstants.PROPERTY_ACCEPTED_BODY,
                        "",
                        "", propertyAssignmentRequest.getHouseId().toString(), NotificationType.TENANT_ACCEPTS_PROPERTY_INVITATION);
                notificationService.saveNotification(notifications);
//                emailService.sendNotificationMail(propertyAssignmentRequest.getEmailId(), NotificationConstants.PROPERTY_ACCEPTED_TITLE, NotificationConstants.PROPERTY_ACCEPTED_BODY);

                //Send Push Notification
                if (deviceId != null) {
                    JSONObject body = new JSONObject();
                    body.put("to", deviceId);
                    body.put("priority", "high");

                    JSONObject notification = new JSONObject();
                    notification.put("title", NotificationConstants.PROPERTY_ACCEPTED_TITLE);
                    notification.put("body", NotificationConstants.PROPERTY_ACCEPTED_BODY);

                    JSONObject data = new JSONObject();
                    body.put("notification", notification);
                    body.put("data", data);
                    pushNotificationsService.sendNotification(body);
                } else {
                    System.out.println("Device Id can't be null");
                }
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

                String sqlDevice = "SELECT device_id FROM user_dladle WHERE emailid=:tenantEmailId";
                String deviceId = this.parameterJdbcTemplate.queryForObject(sqlDevice, map, String.class);

                NotificationView notifications = new NotificationView(
                        userSession.getUser().getEmailId(),
                        propertyAssignmentRequest.getEmailId(),
                        NotificationConstants.PROPERTY_ACCEPTED_TITLE,
                        NotificationConstants.PROPERTY_ACCEPTED_BODY,
                        "",
                        "", propertyAssignmentRequest.getHouseId().toString(), NotificationType.LANDLORD_ACCEPTS_PROPERTY_INVITATION);
                notificationService.saveNotification(notifications);
//                emailService.sendNotificationMail(propertyAssignmentRequest.getEmailId(), NotificationConstants.PROPERTY_ACCEPTED_TITLE, NotificationConstants.PROPERTY_ACCEPTED_BODY);

                //Send Push Notification
                if (deviceId != null) {
                    JSONObject body = new JSONObject();
                    body.put("to", deviceId);
                    body.put("priority", "high");

                    JSONObject notification = new JSONObject();
                    notification.put("title", NotificationConstants.PROPERTY_ACCEPTED_TITLE);
                    notification.put("body", NotificationConstants.PROPERTY_ACCEPTED_BODY);

                    JSONObject data = new JSONObject();
                    body.put("notification", notification);
                    body.put("data", data);
                    pushNotificationsService.sendNotification(body);
                } else {
                    System.out.println("Device Id can't be null");
                }

            } catch (Exception e) {
                throw new Exception("Tenant doesn't exist for the given email Id");
            }

        } else {
            throw new Exception("Not authorised to use this API");
        }
    }


    public void inviteTenant(PropertyInviteRequest propertyInviteRequest) throws Exception {

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        User user = userUtility.findUserByEmail(propertyInviteRequest.getEmailId());

        if (user.getUserType().eqTENANT()) {

            Map<String, Object> map = new HashMap<>();
            map.put("emailId", propertyInviteRequest.getEmailId());
            map.put("houseId", propertyInviteRequest.getHouseId());
            String sql = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";

            String sqlProperty = "SELECT address FROM property INNER JOIN house h2 ON property.id = h2.property_id WHERE h2.id=:houseId";
            String address = this.parameterJdbcTemplate.queryForObject(sqlProperty, map, String.class);

            //save notification
            NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                    propertyInviteRequest.getEmailId(),
                    NotificationConstants.LANDLORD_INVITE_PROPERTY_TITLE,
                    NotificationConstants.LANDLORD_INVITE_PROPERTY_BODY,
                    "landlordEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + propertyInviteRequest.getHouseId() + "," + "propertyAddress:" + address,
                    "", "0", NotificationType.LANDLORD_REQUEST_TENANT);
            notificationService.saveNotification(notifications);

            //Send Email
//        emailService.sendNotificationMail(propertyInviteRequest.getEmailId(), NotificationConstants.LANDLORD_INVITE_PROPERTY_TITLE, NotificationConstants.LANDLORD_INVITE_PROPERTY_BODY);
            try {

                String deviceId = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);
                if (deviceId != null) {
                    JSONObject body = new JSONObject();
                    body.put("to", deviceId);
                    body.put("priority", "high");

                    JSONObject notification = new JSONObject();
                    notification.put("body", NotificationConstants.LANDLORD_INVITE_PROPERTY_BODY);
                    notification.put("title", NotificationConstants.LANDLORD_INVITE_PROPERTY_TITLE);

                    JSONObject data = new JSONObject();
                    data.put("landlordEmailId", userSession.getUser().getEmailId());
                    data.put("houseId", propertyInviteRequest.getHouseId());

                    body.put("notification", notification);
                    body.put("data", data);

                    pushNotificationsService.sendNotification(body);
                } else {
                    System.out.println("Device Id can't be null");
                }
            } catch (Exception e) {
                System.out.println("Device Id can't be null");
            }
        } else {
            throw new Exception("You can only invite a tenant to your property");
        }
    }

    public void requestLandlord(PropertyRequest propertyRequest) throws Exception {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        User user = userUtility.findUserByEmail(propertyRequest.getEmailId());

        if (user.getUserType().eqLANDLORD()) {
            Map<String, Object> map = new HashMap<>();
            map.put("emailId", propertyRequest.getEmailId());
            String sql = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";
            String deviceId = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);

            //save notification
            NotificationView notifications = new NotificationView(
                    userSession.getUser().getEmailId(),
                    propertyRequest.getEmailId(),
                    NotificationConstants.TENANT_REQUEST_PROPERTY_TITLE,
                    NotificationConstants.TENANT_REQUEST_PROPERTY_BODY,
                    "tenantEmailId:" + userSession.getUser().getEmailId(),
                    "", "0", NotificationType.TENANT_REQUEST_LANDLORD);
            notificationService.saveNotification(notifications);

            //Send Email
//        emailService.sendNotificationMail(propertyRequest.getEmailId(), NotificationConstants.TENANT_REQUEST_PROPERTY_TITLE, NotificationConstants.TENANT_REQUEST_PROPERTY_BODY);

            //Send Push Notification
            if (deviceId != null) {
                JSONObject body = new JSONObject();
                body.put("to", deviceId);
                body.put("priority", "high");

                JSONObject notification = new JSONObject();
                notification.put("body", NotificationConstants.TENANT_REQUEST_PROPERTY_BODY);
                notification.put("title", NotificationConstants.TENANT_REQUEST_PROPERTY_TITLE);

                JSONObject data = new JSONObject();
                data.put("landlordEmailId", userSession.getUser().getEmailId());
                body.put("notification", notification);
                body.put("data", data);
                pushNotificationsService.sendNotification(body);
            } else {
                System.out.println("Device Id can't be null");
            }
        } else {
            throw new Exception("You can only request for property to a Landlord");
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
                    NotificationConstants.PROPERTY_REJECTED_TITLE,
                    NotificationConstants.PROPERTY_REJECTED_BODY,
                    "tenantEmailId:" + userSession.getUser().getEmailId(),
                    "", propertyDeclineRequest.getHouseId().toString(), NotificationType.TENANT_REJECTS_PROPERTY_INVITATION);
            notificationService.saveNotification(notifications);
//            emailService.sendNotificationMail(propertyDeclineRequest.getEmailId(), NotificationConstants.PROPERTY_REJECTED_TITLE, NotificationConstants.PROPERTY_REJECTED_BODY);

            //Send Push Notification
            if (deviceId != null) {
                JSONObject body = new JSONObject();
                body.put("to", deviceId);
                body.put("priority", "high");

                JSONObject notification = new JSONObject();
                notification.put("title", NotificationConstants.PROPERTY_REJECTED_TITLE);
                notification.put("body", NotificationConstants.PROPERTY_REJECTED_BODY);

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
                    NotificationConstants.PROPERTY_REJECTED_TITLE,
                    NotificationConstants.PROPERTY_REJECTED_BODY,
                    "tenantEmailId:" + userSession.getUser().getEmailId(),
                    "", "0", NotificationType.LANDLORD_REJECTS_PROPERTY_INVITATION);
            notificationService.saveNotification(notifications);
//            emailService.sendNotificationMail(propertyDeclineRequest.getEmailId(), NotificationConstants.PROPERTY_REJECTED_TITLE, NotificationConstants.PROPERTY_REJECTED_BODY);

            //Send Push Notification
            if (deviceId != null) {
                JSONObject body = new JSONObject();
                body.put("to", deviceId);
                body.put("priority", "high");

                JSONObject notification = new JSONObject();
                notification.put("title", NotificationConstants.PROPERTY_REJECTED_TITLE);
                notification.put("body", NotificationConstants.PROPERTY_REJECTED_BODY);

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
