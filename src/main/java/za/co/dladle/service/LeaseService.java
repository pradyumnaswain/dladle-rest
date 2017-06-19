package za.co.dladle.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.*;
import za.co.dladle.model.LeaseLandlord;
import za.co.dladle.model.LeaseTenant;
import za.co.dladle.model.NotificationType;
import za.co.dladle.model.User;
import za.co.dladle.session.UserSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/14/2017.
 */
@Service
public class LeaseService {

    @Autowired
    private UserServiceUtility userServiceUtility;

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

            Long landlordId = userServiceUtility.findLandlordIdByEmail(userSession.getUser().getEmailId());
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
            Long tenantId = userServiceUtility.findTenantIdByEmail(userSession.getUser().getEmailId());
            map.put("tenantId", tenantId);

            String sql = "SELECT *,lease.id lease_id FROM lease INNER JOIN lease_tenant ON lease.id = lease_tenant.lease_id " +
                    "INNER JOIN house ON lease.house_id = house.id " +
                    "INNER JOIN property ON house.property_id = property.id " +
                    "INNER JOIN landlord ON property.landlord_id = landlord.id " +
                    "INNER JOIN user_dladle ON landlord.user_id = user_dladle.id " +
                    "WHERE tenant_id=:tenantId AND lease_status=TRUE ";
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
            String sql1 = "INSERT INTO lease_tenant(lease_id, tenant_id, joined_date) VALUES (:leaseId,:tenantId,:joinedDate) ON CONFLICT DO NOTHING ";
            this.jdbcTemplate.update(sql1, map);

        } catch (Exception e) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("houseId", houseId)
                    .addValue("leaseStartDate", LocalDate.now())
                    .addValue("leaseEndDate", LocalDate.now().plusYears(1))
                    .addValue("leaseRenewalDate", LocalDate.now().plusYears(1).plusDays(1))
                    .addValue("leaseRenewalNotificationDate", LocalDate.now().plusMonths(11));

            String sql2 = "INSERT INTO lease(lease_start_date, lease_end_date, lease_renewal_date,lease_renewal_notification_date, house_id, lease_status) VALUES (:leaseStartDate,:leaseEndDate,:leaseRenewalDate,:leaseRenewalNotificationDate,:houseId,TRUE ) ON CONFLICT DO NOTHING ";
            this.jdbcTemplate.update(sql2, mapSqlParameterSource, keyHolder, new String[]{"id"});
            map.put("leaseId", keyHolder.getKey().longValue());
            map.put("joinedDate", LocalDate.now());

            String sql1 = "INSERT INTO lease_tenant(lease_id, tenant_id, joined_date) VALUES (:leaseId,:tenantId,:joinedDate)";
            this.jdbcTemplate.update(sql1, map);
        }
    }

    public void terminateLease(LeaseTerminateRequest leaseTerminateRequest) throws Exception {
        Map<String, Object> map = new HashMap<>();
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        map.put("emailId", userSession.getUser().getEmailId());
        map.put("houseId", leaseTerminateRequest.getHouseId());
        map.put("leaseId", leaseTerminateRequest.getLeaseId());

        if (userSession.getUser().getUserType().eqTENANT()) {
            String sql = "SELECT lease.id FROM lease_tenant INNER JOIN lease ON lease_tenant.lease_id = lease.id " +
                    "INNER JOIN tenant ON lease_tenant.tenant_id = tenant.id " +
                    "INNER JOIN user_dladle ON tenant.user_id = user_dladle.id " +
                    "WHERE emailid=:emailId AND lease.house_id=:houseId AND lease.id=:leaseId AND lease_status=TRUE ";
            try {
                Long leaseId = this.jdbcTemplate.queryForObject(sql, map, Long.class);

                if (leaseId != null) {

                    try {
                        String sql1 = "SELECT device_id,emailid FROM user_dladle INNER JOIN landlord ON user_dladle.id = landlord.user_id " +
                                "INNER JOIN property ON landlord.id = property.landlord_id " +
                                "INNER JOIN house ON house.property_id = property.id " +
                                "WHERE house.id=:houseId";

                        UserDeviceEmailId deviceEmailId = this.jdbcTemplate.queryForObject(sql1, map, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));
                        //save notification
                        NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                                deviceEmailId.getEmailId(),
                                "Lease Terminate Request",
                                "Please review and accept/reject this lease terminate request",
                                "tenantEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + leaseTerminateRequest.getHouseId(),
                                "", String.valueOf(leaseTerminateRequest.getHouseId()), NotificationType.LEASE_TERMINATE_REQUEST_TENANT);
                        notificationService.saveNotification(notifications);

                        //Send Email
                        emailService.sendLeaseTerminateRequest(deviceEmailId.getEmailId());

                        if (deviceEmailId.getDeviceId() != null) {
                            JSONObject body = new JSONObject();
                            body.put("to", deviceEmailId.getDeviceId());
                            body.put("priority", "high");

                            JSONObject notification = new JSONObject();
                            notification.put("body", "Please review and accept/reject this lease terminate request");
                            notification.put("title", "Lease Terminate Request");

                            JSONObject data = new JSONObject();
                            data.put("tenantEmailId", userSession.getUser().getEmailId());
                            data.put("houseId", leaseTerminateRequest.getHouseId());

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
                                "INNER JOIN lease ON lease.id= lease_tenant.lease_id " +
                                "WHERE house.id=:houseId";

                        List<UserDeviceEmailId> deviceEmailIdList = this.jdbcTemplate.query(sql1, map, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));
                        for (UserDeviceEmailId deviceEmailId : deviceEmailIdList) {
                            //save notification
                            NotificationView notifications = new NotificationView(userSession.getUser().getEmailId(),
                                    deviceEmailId.getEmailId(),
                                    "Lease Terminate Request",
                                    "Please review and accept/reject this lease terminate request",
                                    "landlordEmailId:" + userSession.getUser().getEmailId() + "," + "houseId:" + leaseTerminateRequest.getHouseId(),
                                    "", String.valueOf(leaseTerminateRequest.getHouseId()), NotificationType.LEASE_TERMINATE_REQUEST_LANDLORD);
                            notificationService.saveNotification(notifications);

                            //Send Email
                            emailService.sendLeaseTerminateRequest(deviceEmailId.getEmailId());

                            if (deviceEmailId.getDeviceId() != null) {
                                JSONObject body = new JSONObject();
                                body.put("to", deviceEmailId.getDeviceId());
                                body.put("priority", "high");

                                JSONObject notification = new JSONObject();
                                notification.put("body", "Please review and accept/reject this lease terminate request");
                                notification.put("title", "Lease Terminate Request");

                                JSONObject data = new JSONObject();
                                data.put("landlordEmailId", userSession.getUser().getEmailId());
                                data.put("houseId", leaseTerminateRequest.getHouseId());

                                body.put("notification", notification);
                                body.put("data", data);

                                pushNotificationsService.sendNotification(body);
                            } else {
                                System.out.println("Device Id can't be null");
                            }
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
}
