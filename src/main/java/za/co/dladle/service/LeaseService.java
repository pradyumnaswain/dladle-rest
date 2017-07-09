package za.co.dladle.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.apiutil.NotificationConstants;
import za.co.dladle.entity.*;
import za.co.dladle.model.LeaseLandlord;
import za.co.dladle.model.LeaseTenant;
import za.co.dladle.model.NotificationType;
import za.co.dladle.model.User;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.thirdparty.AndroidPushNotificationsService;
import za.co.dladle.thirdparty.NotificationServiceSendGridImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by prady on 6/14/2017.
 */
@Service
public class LeaseService {

    @Autowired
    private UserUtility userUtility;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AndroidPushNotificationsService pushNotificationsService;

    @Autowired
    private PushNotificationService notificationService;

    @Autowired
    private NotificationServiceSendGridImpl emailService;


    public LeaseLandlord viewLease(long houseId) throws Exception {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {

            Long landlordId = userUtility.findLandlordIdByEmail(userSession.getUser().getEmailId());
            map.put("landlordId", landlordId);
            map.put("houseId", houseId);

            String sqlCheck = "SELECT landlord_id FROM house INNER JOIN property ON house.property_id = property.id " +
                    "INNER JOIN landlord ON property.landlord_id = landlord.id " +
                    "WHERE house.id=:houseId AND landlord_id=:landlordId";

            try {
                this.jdbcTemplate.queryForObject(sqlCheck, map, Long.class);
            } catch (Exception e) {
                throw new Exception("This House doesn't belong to landlord");
            }
            List<LeaseLandlord> leaseLandlordList = new ArrayList<>();
            String sql = "SELECT * FROM lease  WHERE lease.house_id=:houseId AND lease_status=TRUE ";
            this.jdbcTemplate.query(sql, map, (rs, rowNum) -> {
                LeaseLandlord leaseLandlord = new LeaseLandlord();
                leaseLandlord.setLeaseId(rs.getLong("id"));
                leaseLandlord.setLeaseStartDate(rs.getDate("lease_start_date"));
                leaseLandlord.setLeaseEndDate(rs.getDate("lease_end_date"));
                leaseLandlord.setLeaseRenewalDate(rs.getDate("lease_renewal_date"));
                leaseLandlord.setLeaseTerminateDate(rs.getDate("lease_terminate_date"));

                map.put("leaseId", rs.getLong("id"));

                List<TenantLeaseView> tenantLeaseViews = new ArrayList<>();
                String sql1 = "SELECT * FROM lease_tenant " +
                        " INNER JOIN tenant ON tenant.id=lease_tenant.tenant_id " +
                        " INNER JOIN user_dladle ON user_dladle.id=tenant.user_id " +
                        "WHERE lease_id=:leaseId ";
                this.jdbcTemplate.query(sql1, map, (rs1, rowNum1) -> {
                    TenantLeaseView user = new TenantLeaseView(rs1.getString("emailId"),
                            rs1.getString("first_name"),
                            rs1.getString("last_name"),
                            rs1.getString("cell_number"),
                            rs1.getString("profile_picture"),
                            rs1.getString("id_number"),
                            rs1.getDate("joined_date"));
                    tenantLeaseViews.add(user);
                    return user;
                });
                leaseLandlord.setTenantList(tenantLeaseViews);
                leaseLandlordList.add(leaseLandlord);
                return leaseLandlord;
            });

            if (leaseLandlordList.isEmpty()) {
                return null;
            } else {
                return leaseLandlordList.get(0);
            }
        } else {
            throw new Exception("This API can only accessed via Landlord");
        }
    }

    public LeaseTenant viewLease() throws Exception {
        List<LeaseTenant> leaseTenantList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqTENANT()) {
            Long tenantId = userUtility.findTenantIdByEmail(userSession.getUser().getEmailId());
            map.put("tenantId", tenantId);

            String sql = "SELECT *,lease.id lease_id FROM lease INNER JOIN lease_tenant ON lease.id = lease_tenant.lease_id " +
                    "INNER JOIN house ON lease.house_id = house.id " +
                    "INNER JOIN property ON house.property_id = property.id " +
                    "INNER JOIN landlord ON property.landlord_id = landlord.id " +
                    "INNER JOIN user_dladle ON landlord.user_id = user_dladle.id " +
                    "WHERE tenant_id=:tenantId AND lease.lease_status=TRUE AND lease_tenant.lease_status=TRUE ";
            this.jdbcTemplate.query(sql, map, (rs, rowNum) -> {
                LeaseTenant leaseTenant1 = new LeaseTenant();
                User user = new User(rs.getString("emailId"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("cell_number"),
                        rs.getString("profile_picture"),
                        rs.getString("id_number"));
                leaseTenant1.setLandlord(user);
                PropertyView property = new PropertyView();
                property.setAddress(rs.getString("address"));
                property.setComplexName(rs.getString("complex_name"));
                property.setEstate(rs.getBoolean("isestate"));
                property.setEstateName(rs.getString("estate_name"));
                property.setPlaceImage(rs.getString("image_url"));
                property.setUnitNumber(rs.getString("unit_number"));

                leaseTenant1.setProperty(property);
                leaseTenant1.setLeaseId(rs.getLong("lease_id"));
                leaseTenant1.setLeaseStartDate(rs.getDate("lease_start_date"));
                leaseTenant1.setLeaseEndDate(rs.getDate("lease_end_date"));
                leaseTenant1.setLeaseRenewalDate(rs.getDate("lease_renewal_date"));
                leaseTenant1.setLeaseTerminateDate(rs.getDate("lease_terminate_date"));

                leaseTenantList.add(leaseTenant1);
                return leaseTenant1;
            });

            if (leaseTenantList.isEmpty()) {
                return null;
            } else {
                return leaseTenantList.get(0);
            }
        } else {
            throw new Exception("This API can only accessed via Tenant");
        }
    }

    void createOrUpdateLease(Long houseId, Long tenantId) {
        Map<String, Object> map = new HashMap<>();
        map.put("houseId", houseId);
        map.put("tenantId", tenantId);
        try {
            String sql = "SELECT id FROM lease WHERE house_id=:houseId AND lease_status=TRUE ";
            Long leaseId = this.jdbcTemplate.queryForObject(sql, map, Long.class);
            map.put("leaseId", leaseId);
            map.put("joinedDate", LocalDate.now());
            String sql1 = "INSERT INTO lease_tenant(lease_id, tenant_id, joined_date,lease_status) VALUES (:leaseId,:tenantId,:joinedDate,TRUE ) ON CONFLICT DO NOTHING ";
            this.jdbcTemplate.update(sql1, map);

        } catch (Exception e) {
            LocalDate leaseStartDate;
            if (LocalDate.now().getDayOfMonth() >= 15) {
                leaseStartDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
            } else {
                leaseStartDate = LocalDate.now().plusMonths(0).withDayOfMonth(1);
            }
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("houseId", houseId)
                    .addValue("leaseStartDate", leaseStartDate)
                    .addValue("leaseEndDate", leaseStartDate)
                    .addValue("leaseRenewalDate", leaseStartDate.plusYears(1).plusDays(1))
                    .addValue("leaseRenewalNotificationDate", leaseStartDate.plusMonths(11));

            String sql2 = "INSERT INTO lease(lease_start_date, lease_end_date, lease_renewal_date,lease_renewal_notification_date, house_id, lease_status) VALUES (:leaseStartDate,:leaseEndDate,:leaseRenewalDate,:leaseRenewalNotificationDate,:houseId,TRUE ) ON CONFLICT DO NOTHING ";
            this.jdbcTemplate.update(sql2, mapSqlParameterSource, keyHolder, new String[]{"id"});
            map.put("leaseId", keyHolder.getKey().longValue());
            map.put("joinedDate", LocalDate.now());

            String sql1 = "INSERT INTO lease_tenant(lease_id, tenant_id, joined_date,lease_status) VALUES (:leaseId,:tenantId,:joinedDate,TRUE )";
            this.jdbcTemplate.update(sql1, map);
        }
    }

    public void terminateLease(LeaseTerminateRequest leaseTerminateRequest) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        Map<String, Object> map = new HashMap<>();
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        map.put("emailId", userSession.getUser().getEmailId());
        map.put("houseId", leaseTerminateRequest.getHouseId());
        map.put("leaseId", leaseTerminateRequest.getLeaseId());

        if (userSession.getUser().getUserType().eqTENANT()) {
            String sql = "SELECT lease.id FROM lease_tenant INNER JOIN lease ON lease_tenant.lease_id = lease.id " +
                    "INNER JOIN tenant ON lease_tenant.tenant_id = tenant.id " +
                    "INNER JOIN user_dladle ON tenant.user_id = user_dladle.id " +
                    "WHERE emailid=:emailId AND lease.house_id=:houseId AND lease.id=:leaseId AND lease.lease_status=TRUE ";
            try {
                Long leaseId = this.jdbcTemplate.queryForObject(sql, map, Long.class);

                if (leaseId != null) {

                    try {
                        String sql1 = "SELECT device_id,emailid FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id " +
                                "INNER JOIN property ON landlord.id = property.landlord_id " +
                                "INNER JOIN house ON house.property_id = property.id " +
                                "WHERE house.id=:houseId";

                        UserDeviceEmailId deviceEmailId = this.jdbcTemplate.queryForObject(sql1, map, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));

                        completionService.submit(() -> {
                            //save notification
                            NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                                    deviceEmailId.getEmailId(),
                                    NotificationConstants.LEASE_TERMINATE_TITLE,
                                    NotificationConstants.LEASE_TERMINATE_BODY,
                                    "tenantEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + leaseTerminateRequest.getHouseId() + "," + "leaseId:" + leaseTerminateRequest.getLeaseId(),
                                    "", String.valueOf(leaseTerminateRequest.getHouseId()), NotificationType.LEASE_TERMINATE_REQUEST_TENANT);
                            notificationService.saveNotification(notifications);

                            return null;
                        });
                        completionService.submit(() -> {
                            //Send Email
                            emailService.sendNotificationMail(deviceEmailId.getEmailId(), NotificationConstants.LEASE_TERMINATE_TITLE, NotificationConstants.LEASE_TERMINATE_BODY);

                            return null;
                        });
                        completionService.submit(() -> {
                            if (deviceEmailId.getDeviceId() != null) {
                                JSONObject body = new JSONObject();
                                body.put("to", deviceEmailId.getDeviceId());
                                body.put("priority", "high");

                                JSONObject notification = new JSONObject();
                                notification.put("body", NotificationConstants.LEASE_TERMINATE_BODY);
                                notification.put("title", NotificationConstants.LEASE_TERMINATE_TITLE);

                                JSONObject data = new JSONObject();
                                data.put("tenantEmailId", userSession.getUser().getEmailId());
                                data.put("houseId", leaseTerminateRequest.getHouseId());
                                data.put("leaseId", leaseTerminateRequest.getLeaseId());

                                body.put("notification", notification);
                                body.put("data", data);

                                pushNotificationsService.sendNotification(body);
                            } else {
                                System.out.println("Device Id can't be null");
                            }

                            return null;
                        });
                        for (int i = 0; i < 3; i++) {
                            completionService.take().get();
                            // Some processing here
                        }
                    } catch (Exception e) {
                        System.out.println("Device Id can't be null");
                    }

                } else {
                    throw new Exception("Lease doesn't belong to you");
                }
            } catch (Exception e) {
                throw new Exception("Lease doesn't belong to you");
            }

        } else if (userSession.getUser().getUserType().eqLANDLORD()) {
            String sql = "SELECT lease.id FROM  lease " +
                    "INNER JOIN house ON lease.house_id= house.id " +
                    "INNER JOIN property ON house.property_id= property.id " +
                    "INNER JOIN landlord ON property.landlord_id= landlord.id " +
                    "INNER JOIN user_dladle ON landlord.user_id = user_dladle.id " +
                    "WHERE emailid=:emailId AND lease.house_id=:houseId AND lease.id=:leaseId AND lease_status=TRUE ";
            try {
                Long leaseId = this.jdbcTemplate.queryForObject(sql, map, Long.class);

                if (leaseId != null) {
                    try {
                        String sql1 = "SELECT device_id,emailid FROM user_dladle INNER JOIN tenant ON user_dladle.id = tenant.user_id " +
                                "INNER JOIN lease_tenant ON lease_tenant.tenant_id = tenant.id " +
                                "INNER JOIN house ON house.id= tenant.house_id " +
                                "WHERE house.id=:houseId AND lease_status=TRUE";

                        List<UserDeviceEmailId> deviceEmailIdList = this.jdbcTemplate.query(sql1, map, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));
                        for (UserDeviceEmailId deviceEmailId : deviceEmailIdList) {
                            completionService.submit(() -> {
                                //save notification
                                NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                                        deviceEmailId.getEmailId(),
                                        NotificationConstants.LEASE_TERMINATE_TITLE,
                                        NotificationConstants.LEASE_TERMINATE_BODY,
                                        "landlordEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + leaseTerminateRequest.getHouseId() + "," + "leaseId:" + leaseTerminateRequest.getLeaseId(),
                                        "", String.valueOf(leaseTerminateRequest.getHouseId()), NotificationType.LEASE_TERMINATE_REQUEST_LANDLORD);
                                notificationService.saveNotification(notifications);

                                //Send Email
                                emailService.sendNotificationMail(deviceEmailId.getEmailId(), NotificationConstants.LEASE_TERMINATE_TITLE, NotificationConstants.LEASE_TERMINATE_BODY);

                                if (deviceEmailId.getDeviceId() != null) {
                                    JSONObject body = new JSONObject();
                                    body.put("to", deviceEmailId.getDeviceId());
                                    body.put("priority", "high");

                                    JSONObject notification = new JSONObject();
                                    notification.put("body", NotificationConstants.LEASE_TERMINATE_BODY);
                                    notification.put("title", NotificationConstants.LEASE_TERMINATE_TITLE);

                                    JSONObject data = new JSONObject();
                                    data.put("landlordEmailId", userSession.getUser().getEmailId());
                                    data.put("houseId", leaseTerminateRequest.getHouseId());
                                    data.put("leaseId", leaseTerminateRequest.getLeaseId());

                                    body.put("notification", notification);
                                    body.put("data", data);

                                    pushNotificationsService.sendNotification(body);
                                } else {
                                    System.out.println("Device Id can't be null");
                                }
                                return null;
                            });
                        }
                        for (UserDeviceEmailId ignored : deviceEmailIdList) {
                            completionService.take().get();
                        }

                    } catch (Exception e) {
                        System.out.println("Device Id can't be null");
                    }

                } else {
                    throw new Exception("Lease doesn't belong to you");
                }
            } catch (Exception e) {
                throw new Exception("Lease doesn't belong to you");
            }
        }
    }

    public void acceptTerminateLease(LeaseTerminateRequest leaseTerminateRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("houseId", leaseTerminateRequest.getHouseId());
        map.put("leaseId", leaseTerminateRequest.getLeaseId());
        map.put("leaveDate", LocalDateTime.now());

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqTENANT()) {
            String sqlLease = "UPDATE lease SET lease_status=FALSE,lease_terminate_date=:leaveDate WHERE lease.id=:leaseId AND lease.house_id=:houseId AND lease_status=TRUE ";

            this.jdbcTemplate.update(sqlLease, map);

            String sqlLeaseTenant = "UPDATE lease_tenant SET lease_status=FALSE,leave_date=:leaveDate WHERE lease_tenant.lease_id=:leaseId AND lease_status=TRUE ";

            this.jdbcTemplate.update(sqlLeaseTenant, map);

            String sqlTenant = "UPDATE tenant SET house_id=NULL WHERE tenant.id IN (SELECT lease_tenant.tenant_id FROM lease_tenant WHERE lease_tenant.lease_id=:leaseId AND lease_status=FALSE )";

            this.jdbcTemplate.update(sqlTenant, map);
            try {
                String sql1 = "SELECT device_id,emailid FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id " +
                        "INNER JOIN property ON landlord.id = property.landlord_id " +
                        "INNER JOIN house ON house.property_id = property.id " +
                        "WHERE house.id=:houseId";

                UserDeviceEmailId deviceEmailId = this.jdbcTemplate.queryForObject(sql1, map, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));
                //save notification
                NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                        deviceEmailId.getEmailId(),
                        NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_TITLE,
                        NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_BODY,
                        "tenantEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + leaseTerminateRequest.getHouseId() + "," + "leaseId:" + leaseTerminateRequest.getLeaseId(),
                        "", String.valueOf(leaseTerminateRequest.getHouseId()), NotificationType.LEASE_TERMINATE_TENANT_ACCEPT);
                notificationService.saveNotification(notifications);

                //Send Email
                emailService.sendNotificationMail(deviceEmailId.getEmailId(), NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_TITLE, NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_BODY);

                if (deviceEmailId.getDeviceId() != null) {
                    JSONObject body = new JSONObject();
                    body.put("to", deviceEmailId.getDeviceId());
                    body.put("priority", "high");

                    JSONObject notification = new JSONObject();
                    notification.put("body", NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_BODY);
                    notification.put("title", NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_TITLE);

                    JSONObject data = new JSONObject();
                    data.put("tenantEmailId", userSession.getUser().getEmailId());
                    data.put("houseId", leaseTerminateRequest.getHouseId());
                    data.put("leaseId", leaseTerminateRequest.getLeaseId());

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
            String sqlLease = "UPDATE lease SET lease_status=FALSE,lease_terminate_date=:leaveDate WHERE lease.id=:leaseId AND lease.house_id=:houseId AND lease_status=TRUE ";

            this.jdbcTemplate.update(sqlLease, map);

            String sqlLeaseTenant = "UPDATE lease_tenant SET lease_status=FALSE,leave_date=:leaveDate WHERE lease_tenant.lease_id=:leaseId AND lease_status=TRUE ";

            this.jdbcTemplate.update(sqlLeaseTenant, map);

            String sqlTenant = "UPDATE tenant SET house_id=NULL WHERE tenant.id IN (SELECT lease_tenant.tenant_id FROM lease_tenant WHERE lease_tenant.lease_id=:leaseId AND lease_status=FALSE )";

            this.jdbcTemplate.update(sqlTenant, map);
            try {
                String sql1 = "SELECT device_id,emailid FROM user_dladle INNER JOIN tenant ON user_dladle.id = tenant.user_id " +
                        "INNER JOIN house ON tenant.house_id = house.id " +
                        "WHERE house.id=:houseId";

                UserDeviceEmailId deviceEmailId = this.jdbcTemplate.queryForObject(sql1, map, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));
                //save notification
                NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                        deviceEmailId.getEmailId(),
                        NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_TITLE,
                        NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_BODY,
                        "landlordEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + leaseTerminateRequest.getHouseId() + "," + "leaseId:" + leaseTerminateRequest.getLeaseId(),
                        "", String.valueOf(leaseTerminateRequest.getHouseId()), NotificationType.LEASE_TERMINATE_TENANT_ACCEPT);
                notificationService.saveNotification(notifications);

                //Send Email
                emailService.sendNotificationMail(deviceEmailId.getEmailId(), NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_TITLE, NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_BODY);

                if (deviceEmailId.getDeviceId() != null) {
                    JSONObject body = new JSONObject();
                    body.put("to", deviceEmailId.getDeviceId());
                    body.put("priority", "high");

                    JSONObject notification = new JSONObject();
                    notification.put("body", NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_BODY);
                    notification.put("title", NotificationConstants.LEASE_TERMINATE_TENANT_ACCEPT_TITLE);

                    JSONObject data = new JSONObject();
                    data.put("landlordEmailId", userSession.getUser().getEmailId());
                    data.put("houseId", leaseTerminateRequest.getHouseId());
                    data.put("leaseId", leaseTerminateRequest.getLeaseId());

                    body.put("notification", notification);
                    body.put("data", data);

                    pushNotificationsService.sendNotification(body);
                } else {
                    System.out.println("Device Id can't be null");
                }
            } catch (Exception e) {
                System.out.println("Device Id can't be null");
            }
        }
    }

    public void leaveLease() throws Exception {

        Map<String, Object> map = new HashMap<>();
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        String emailId = userSession.getUser().getEmailId();

        if (userSession.getUser().getUserType().eqTENANT()) {
            Long tenantId = userUtility.findTenantIdByEmail(userSession.getUser().getEmailId());

            map.put("userId", tenantId);
            map.put("leaveDate", LocalDateTime.now());

            String sql1 = "SELECT device_id,emailid FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id " +
                    "INNER JOIN property ON landlord.id = property.landlord_id " +
                    "INNER JOIN house ON house.property_id = property.id " +
                    "INNER JOIN tenant ON house.id = tenant.house_id " +
                    "WHERE tenant.id=:userId";

            UserDeviceEmailId userDeviceEmailId = this.jdbcTemplate.queryForObject(sql1, map, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));

            String sql2 = "SELECT tenants_count FROM house INNER JOIN lease ON house.id = lease.house_id " +
                    "INNER JOIN lease_tenant ON lease.id = lease_tenant.lease_id WHERE tenant_id=:userId AND lease.lease_status=TRUE AND lease_tenant.lease_status=TRUE ";

            int tenantCount = this.jdbcTemplate.queryForObject(sql2, map, Integer.class);

            if (tenantCount == 1) {
                String sql3 = "SELECT * FROM lease INNER JOIN lease_tenant ON lease.id = lease_tenant.lease_id WHERE tenant_id=:userId AND lease.lease_status=TRUE AND lease_tenant.lease_status=TRUE ";

                LeaseTerminateRequest leaseTerminateRequest = this.jdbcTemplate.queryForObject(sql3, map, (rs, rowNum) -> new LeaseTerminateRequest(rs.getLong("house_id"), rs.getLong("lease_id")));

                terminateLease(leaseTerminateRequest);
            } else {

                String sqlLeaseTenant = "UPDATE lease_tenant SET lease_status=FALSE,leave_date=:leaveDate WHERE lease_tenant.tenant_id=:userId AND lease_status=TRUE ";

                this.jdbcTemplate.update(sqlLeaseTenant, map);

                String sqlTenant = "UPDATE tenant SET house_id=NULL WHERE tenant.id=:userId";

                this.jdbcTemplate.update(sqlTenant, map);
                try {
                    //save notification
                    NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                            userDeviceEmailId.getEmailId(),
                            NotificationConstants.LEASE_LEAVES_TENANT_TITLE,
                            NotificationConstants.LEASE_LEAVES_TENANT_BODY,
                            "tenantEmailId:" + userSession.getUser().getEmailId(),
                            "", null, NotificationType.LEASE_LEAVES_TENANT);
                    notificationService.saveNotification(notifications);
                    NotificationView notification1 = new NotificationView(
                            userSession.getUser().getEmailId(), userDeviceEmailId.getEmailId(),
                            NotificationConstants.RATE_TENANT_TITLE,
                            NotificationConstants.RATE_TENANT_BODY,
                            "tenantEmailId:" + userSession.getUser().getEmailId(),
                            "", null, NotificationType.RATE_TENANT);
                    notificationService.saveNotification(notification1);
                    NotificationView notification2 = new NotificationView(
                            userDeviceEmailId.getEmailId(), userSession.getUser().getEmailId(),
                            NotificationConstants.RATE_LANDLORD_TITLE,
                            NotificationConstants.RATE_LANDLORD_BODY,
                            "landlordEmailId:" + userDeviceEmailId.getEmailId(),
                            "", null, NotificationType.RATE_LANDLORD);
                    notificationService.saveNotification(notification2);

// TODO: 6/24/2017 update Notification Type
                    //Send Email
                    emailService.sendNotificationMail(emailId, NotificationConstants.LEASE_LEAVES_TENANT_TITLE, NotificationConstants.LEASE_LEAVES_TENANT_BODY);

                    if (userDeviceEmailId.getDeviceId() != null) {
                        JSONObject body = new JSONObject();
                        body.put("to", userDeviceEmailId.getDeviceId());
                        body.put("priority", "high");

                        JSONObject notification = new JSONObject();
                        notification.put("body", NotificationConstants.LEASE_LEAVES_TENANT_BODY);
                        notification.put("title", NotificationConstants.LEASE_LEAVES_TENANT_TITLE);

                        JSONObject data = new JSONObject();
                        data.put("tenantEmailId", userSession.getUser().getEmailId());

                        body.put("notification", notification);
                        body.put("data", data);

                        pushNotificationsService.sendNotification(body);
                    } else {
                        System.out.println("Device Id can't be null");
                    }
                } catch (Exception e) {
                    System.out.println("Device Id can't be null");
                }
            }
        } else {
            throw new Exception("You must be a Tenant to access this");
        }
    }


    public void removeTenantFromLease(String emailId) throws Exception {
        Map<String, Object> map = new HashMap<>();

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        if (userSession.getUser().getUserType().eqLANDLORD()) {
            Long tenantId = userUtility.findTenantIdByEmail(emailId);

            map.put("userId", tenantId);
            map.put("leaveDate", LocalDateTime.now());

            String sqlLeaseTenant = "UPDATE lease_tenant SET lease_status=FALSE,leave_date=:leaveDate WHERE lease_tenant.tenant_id=:userId AND lease_status=TRUE ";

            this.jdbcTemplate.update(sqlLeaseTenant, map);

            String sqlTenant = "UPDATE tenant SET house_id=NULL WHERE tenant.id=:userId";

            this.jdbcTemplate.update(sqlTenant, map);

            try {
                String sql1 = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";

                //save notification
                NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                        emailId,
                        NotificationConstants.LEASE_REMOVES_TENANT_TITLE,
                        NotificationConstants.LEASE_REMOVES_TENANT_BODY,
                        "landlordEmailId:" + userSession.getUser().getEmailId(),
                        "", null, NotificationType.LEASE_REMOVES_TENANT);
                notificationService.saveNotification(notifications);
                NotificationView notification1 = new NotificationView(
                        emailId, userSession.getUser().getEmailId(),
                        NotificationConstants.RATE_TENANT_TITLE,
                        NotificationConstants.RATE_TENANT_BODY,
                        "tenantEmailId:" + emailId,
                        "", null, NotificationType.RATE_TENANT);
                notificationService.saveNotification(notification1);
                NotificationView notification2 = new NotificationView(
                        userSession.getUser().getEmailId(), emailId,
                        NotificationConstants.RATE_LANDLORD_TITLE,
                        NotificationConstants.RATE_LANDLORD_BODY,
                        "landlordEmailId:" + userSession.getUser().getEmailId(),
                        "", null, NotificationType.RATE_LANDLORD);
                notificationService.saveNotification(notification2);
// TODO: 6/24/2017 update Notification Type
                //Send Email
                emailService.sendNotificationMail(emailId, NotificationConstants.LEASE_REMOVES_TENANT_TITLE, NotificationConstants.LEASE_REMOVES_TENANT_BODY);

                String deviceId = this.jdbcTemplate.queryForObject(sql1, map, String.class);
                if (deviceId != null) {
                    JSONObject body = new JSONObject();
                    body.put("to", deviceId);
                    body.put("priority", "high");

                    JSONObject notification = new JSONObject();
                    notification.put("body", NotificationConstants.LEASE_REMOVES_TENANT_BODY);
                    notification.put("title", NotificationConstants.LEASE_REMOVES_TENANT_TITLE);

                    JSONObject data = new JSONObject();
                    data.put("landlordEmailId", userSession.getUser().getEmailId());

                    body.put("notification", notification);
                    body.put("data", data);

                    pushNotificationsService.sendNotification(body);

                    JSONObject body1 = new JSONObject();
                    body1.put("to", deviceId);
                    body1.put("priority", "high");

                    JSONObject notification3 = new JSONObject();
                    notification3.put("body", NotificationConstants.RATE_TENANT_BODY);
                    notification3.put("title", NotificationConstants.RATE_TENANT_TITLE);

                    JSONObject data1 = new JSONObject();
                    data1.put("landlordEmailId", userSession.getUser().getEmailId());

                    body1.put("notification", notification);
                    body1.put("data", data);

                    pushNotificationsService.sendNotification(body1);
                    // TODO: 7/9/2017 send Notification to Landlord

                } else {
                    System.out.println("Device Id can't be null");
                }
            } catch (Exception e) {
                System.out.println("Device Id can't be null");
            }

        } else {
            throw new Exception("You must be a Landlord to access this");
        }
    }

    public void rejectTerminateLease(LeaseTerminateRequest leaseTerminateRequest) {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Map<String, Object> map = new HashMap<>();
        map.put("houseId", leaseTerminateRequest.getHouseId());
        map.put("leaseId", leaseTerminateRequest.getLeaseId());
        try {
            String sql1 = "SELECT device_id,emailid FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id " +
                    "INNER JOIN property ON landlord.id = property.landlord_id " +
                    "INNER JOIN house ON house.property_id = property.id " +
                    "WHERE house.id=:houseId";

            UserDeviceEmailId deviceEmailId = this.jdbcTemplate.queryForObject(sql1, map, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));
            //save notification
            NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                    deviceEmailId.getEmailId(),
                    NotificationConstants.LEASE_TERMINATE_TENANT_REJECT_TITLE,
                    NotificationConstants.LEASE_TERMINATE_TENANT_REJECT_BODY,
                    "tenantEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + leaseTerminateRequest.getHouseId() + "," + "leaseId:" + leaseTerminateRequest.getLeaseId(),
                    "", String.valueOf(leaseTerminateRequest.getHouseId()), NotificationType.LEASE_TERMINATE_TENANT_REJECT);
            notificationService.saveNotification(notifications);

            //Send Email
            emailService.sendNotificationMail(deviceEmailId.getEmailId(), NotificationConstants.LEASE_TERMINATE_TENANT_REJECT_TITLE, NotificationConstants.LEASE_TERMINATE_TENANT_REJECT_BODY);

            if (deviceEmailId.getDeviceId() != null) {
                JSONObject body = new JSONObject();
                body.put("to", deviceEmailId.getDeviceId());
                body.put("priority", "high");

                JSONObject notification = new JSONObject();
                notification.put("body", NotificationConstants.LEASE_TERMINATE_TENANT_REJECT_BODY);
                notification.put("title", NotificationConstants.LEASE_TERMINATE_TENANT_REJECT_TITLE);

                JSONObject data = new JSONObject();
                data.put("tenantEmailId", userSession.getUser().getEmailId());
                data.put("houseId", leaseTerminateRequest.getHouseId());
                data.put("leaseId", leaseTerminateRequest.getLeaseId());

                body.put("notification", notification);
                body.put("data", data);

                pushNotificationsService.sendNotification(body);
            } else {
                System.out.println("Device Id can't be null");
            }
        } catch (Exception e) {
            System.out.println("Device Id can't be null");
        }
    }
}
