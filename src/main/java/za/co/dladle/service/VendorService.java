package za.co.dladle.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.dladle.apiutil.DateUtil;
import za.co.dladle.apiutil.NotificationConstants;
import za.co.dladle.entity.*;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.mapper.DocumentTypeMapper;
import za.co.dladle.mapper.ServiceStatusMapper;
import za.co.dladle.mapper.ServiceTypeMapper;
import za.co.dladle.model.NotificationType;
import za.co.dladle.model.ServiceStatus;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.thirdparty.AndroidPushNotificationsService;
import za.co.dladle.thirdparty.FileManagementServiceCloudinaryImpl;
import za.co.dladle.thirdparty.NotificationService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by prady on 6/27/2017.
 */
@Service
public class VendorService {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserUtility userUtility;

    @Autowired
    private FileManagementServiceCloudinaryImpl fileManagementServiceCloudinary;

    @Autowired
    private PushNotificationService notificationService;

    @Autowired
    private AndroidPushNotificationsService pushNotificationsService;

    @Value("{vendor.selection.engine}")
    private String url;


    public void requestVendor(VendorServiceRequest vendorServiceRequest) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        UserSession session = applicationContext.getBean(UserSession.class);

        Long userId = userUtility.findUserIdByEmail(session.getUser().getEmailId());

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("serviceType", ServiceTypeMapper.getServiceType(vendorServiceRequest.getServiceType()))
                .addValue("serviceRequestTime", LocalDateTime.now())
                .addValue("requestUserId", userId)
                .addValue("houseId", vendorServiceRequest.getHouseId());

        mapSqlParameterSource.addValue("serviceStatus", ServiceStatusMapper.getServiceStatus(ServiceStatus.REQUESTED))
                .addValue("emergency", vendorServiceRequest.isEmergency())
                .addValue("description", vendorServiceRequest.getServiceNote())
                .addValue("needTime", DateUtil.stringToLocalDateTime(vendorServiceRequest.getServiceNeedTime()));

        KeyHolder keyHolder = new GeneratedKeyHolder();


        String sql = "INSERT INTO service(service_type_id, service_request_time, service_requester_user_id, service_status_id, service_need_time, emergency, service_description,house_id) " +
                "VALUES (:serviceType,:serviceRequestTime,:requestUserId,:serviceStatus,:needTime,:emergency,:description,:houseId)";

        this.jdbcTemplate.update(sql, mapSqlParameterSource, keyHolder, new String[]{"id"});

        if (vendorServiceRequest.getServiceDocuments() != null) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (ServiceDocuments file : vendorServiceRequest.getServiceDocuments()) {
                completionService.submit(() -> {
                    Map<String, Object> map = new HashMap<>();

                    String imageUrl = fileManagementServiceCloudinary.upload(file.getBase64(), file.getFileName());

                    map.put("serviceId", keyHolder.getKey().longValue());
                    map.put("imageUrl", imageUrl);
                    map.put("documentType", DocumentTypeMapper.getDocumentType(file.getDocumentType()));

                    list.add(map);
                    return null;
                });
            }
            for (ServiceDocuments file : vendorServiceRequest.getServiceDocuments()) {
                completionService.take().get();
            }

            String sql1 = "INSERT INTO service_documents (service_id, url,document_type) VALUES (:serviceId,:imageUrl,:documentType)";
            this.jdbcTemplate.batchUpdate(sql1, list.toArray(new Map[vendorServiceRequest.getServiceDocuments().size()]));
        }
        List<VendorAtWorkView> vendorsAtWork = getVendorsAtWork(ServiceTypeMapper.getServiceType(vendorServiceRequest.getServiceType()));

        if (!vendorsAtWork.isEmpty()) {
            for (VendorAtWorkView vendorAtWorkView : vendorsAtWork) {
                completionService.submit(() -> {
                    mapSqlParameterSource.addValue("vendorId", vendorAtWorkView.getVendorId());
                    String sql1 = "SELECT * FROM dladle.public.user_dladle INNER JOIN dladle.public.vendor ON user_dladle.id = vendor.user_id WHERE vendor.id=:vendorId";
                    UserDeviceEmailId deviceEmailId = this.jdbcTemplate.queryForObject(sql1, mapSqlParameterSource, (rs, rowNum) -> new UserDeviceEmailId(rs.getString("device_id"), rs.getString("emailid")));

                    NotificationView notifications = new NotificationView(
                            session.getUser().getEmailId(),
                            deviceEmailId.getEmailId(),
                            NotificationConstants.SERVICE_REQUEST_TITLE,
                            NotificationConstants.SERVICE_REQUEST_BODY,
                            "serviceId:" + keyHolder.getKey().longValue(),
                            "", "0", NotificationType.SERVICE_REQUEST);
                    notificationService.saveNotification(notifications);

                    //Send Push Notification
                    if (deviceEmailId.getDeviceId() != null) {
                        JSONObject body = new JSONObject();
                        body.put("to", deviceEmailId.getDeviceId());
                        body.put("priority", "high");

                        JSONObject notification = new JSONObject();
                        notification.put("title", NotificationConstants.SERVICE_REQUEST_TITLE);
                        notification.put("body", NotificationConstants.SERVICE_REQUEST_BODY);

                        JSONObject data = new JSONObject();
                        data.put("serviceId", keyHolder.getKey().longValue());
                        body.put("notification", notification);
                        body.put("data", data);
                        pushNotificationsService.sendNotification(body);
                    } else {
                        System.out.println("Device Id can't be null");
                    }
                    return null;
                });
                for (int i = 0; i < vendorsAtWork.size(); i++) {
                    completionService.take().get();
                    // Some processing here
                }
            }
            populateVendorWorkTimeline(vendorsAtWork, keyHolder.getKey().longValue());
        } else {
            throw new Exception("Currently No vendor at Work");
        }
        // TODO: 7/2/2017 Find Nearest Vendors and populate service_estimations and send notifications
    }

    private List<VendorAtWorkView> getVendorsAtWork(Integer serviceType) {
        Map<String, Object> map = new HashMap<>();
        map.put("serviceType", serviceType);
        List<VendorAtWorkView> vendorAtWorkViews = new ArrayList<>();
        String sql = "SELECT * FROM vendor_work_timeline " +
                "INNER JOIN vendor ON vendor_work_timeline.vendor_id = vendor.id " +
                "INNER JOIN service_type ON vendor.service_type_id = service_type.id " +
                "INNER JOIN years_exp ON vendor.experience_id = years_exp.id " +
                "INNER JOIN user_dladle ON vendor.user_id = user_dladle.id " +
                "LEFT JOIN rating ON user_dladle.id = rating.rated_user  " +
                "WHERE current_work_status=TRUE AND service_type_id=:serviceType";
        this.jdbcTemplate.query(sql, map, (rs, rowNum) -> {
            VendorAtWorkView vendorAtWorkView = new VendorAtWorkView(rs.getLong("vendor_id"),
                    rs.getString("current_location"), rs.getString("name"), rs.getDouble("value"));
            vendorAtWorkViews.add(vendorAtWorkView);
            return vendorAtWorkView;

        });
        return vendorAtWorkViews;
    }

    public List<VendorAtWorkView> getNearestVendors(List<VendorAtWorkView> vendorAtWorkViews, String
            propertyLocation) throws InterruptedException, ApiException, IOException {
        GeoApiContext geoApiContext = new GeoApiContext();
        geoApiContext.setApiKey("AIzaSyBBD8kFX9-dZqyXoNs6KsgiuKlhSGkvU28");
        List<String> locations = new ArrayList<>();
        for (VendorAtWorkView vendorAtWorkView : vendorAtWorkViews) {
            locations.add(vendorAtWorkView.getCurrentLocation());
        }
        String[] destinations = locations.toArray(new String[locations.size()]);

        DistanceMatrix distanceMatrix = DistanceMatrixApi.newRequest(geoApiContext)
                .origins(propertyLocation)
                .destinations(destinations)
                .mode(TravelMode.DRIVING).
                        await();

        DistanceMatrixRow[] rows = distanceMatrix.rows;
        List<VendorAtWorkView> views = new ArrayList<>();
        for (VendorAtWorkView vendorAtWorkView : vendorAtWorkViews) {
            VendorAtWorkView vendorAtWorkView1 = new VendorAtWorkView();
//            vendorAtWorkView1.s
            views.add(vendorAtWorkView1);

        }

        return views;
    }

    private void populateVendorWorkTimeline(List<VendorAtWorkView> vendorAtWorkViews, long serviceId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (VendorAtWorkView vendorAtWorkView : vendorAtWorkViews) {
            Map<String, Object> map = new HashMap<>();

            map.put("serviceId", serviceId);
            map.put("vendorId", vendorAtWorkView.getVendorId());
            map.put("experience", vendorAtWorkView.getExperience());
            map.put("proximity", vendorAtWorkView.getProximity());
            map.put("rating", vendorAtWorkView.getRating());

            list.add(map);
        }
        String sql1 = "INSERT INTO service_estimations (service_id, vendor_id, experience, proximity, rating) VALUES (:serviceId,:vendorId,:experience,:proximity,:rating)";
        this.jdbcTemplate.batchUpdate(sql1, list.toArray(new Map[vendorAtWorkViews.size()]));
    }

    public void onWork(VendorOnWork vendorOnWork) throws UserNotFoundException {
        UserSession session = applicationContext.getBean(UserSession.class);

        Long userId = userUtility.findVendorIdByEmail(session.getUser().getEmailId());

        Map<String, Object> map = new HashMap<>();

        map.put("vendorId", userId);
        map.put("time", LocalDateTime.now());
        map.put("location", vendorOnWork.getCurrentLocation());

        String sql = "SELECT count(*) FROM vendor_work_timeline WHERE vendor_id=:vendorId AND current_work_status=TRUE ";
        Integer count = this.jdbcTemplate.queryForObject(sql, map, Integer.class);
        if (count == 0) {
            String sql1 = "INSERT INTO vendor_work_timeline(vendor_id, current_work_status, on_work_from, current_location) VALUES " +
                    "(:vendorId,TRUE ,:time,:location)";
            this.jdbcTemplate.update(sql1, map);
        }
    }

    public void offWork() throws UserNotFoundException {
        UserSession session = applicationContext.getBean(UserSession.class);

        Long userId = userUtility.findVendorIdByEmail(session.getUser().getEmailId());

        Map<String, Object> map = new HashMap<>();

        map.put("vendorId", userId);
        map.put("time", LocalDateTime.now());

        String sql1 = "UPDATE vendor_work_timeline SET current_work_status=FALSE ,on_work_to=:time WHERE vendor_id=:vendorId AND current_work_status=TRUE ";
        this.jdbcTemplate.update(sql1, map);
    }

    public VendorWorkStatus currentStatus() throws UserNotFoundException {
        UserSession session = applicationContext.getBean(UserSession.class);

        Long userId = userUtility.findVendorIdByEmail(session.getUser().getEmailId());

        Map<String, Object> map = new HashMap<>();

        map.put("vendorId", userId);
        String sql = "SELECT * FROM vendor_work_timeline WHERE vendor_id=:vendorId AND current_work_status=TRUE ";
        try {
            return this.jdbcTemplate.queryForObject(sql, map, (rs, rowNum) -> new VendorWorkStatus(rs.getBoolean("current_work_status"), rs.getString("on_work_from"), rs.getString("on_work_to")));
        } catch (Exception e) {
            return new VendorWorkStatus(false, null, null);
        }
    }

    public ServiceView viewWork(Long serviceId) throws Exception {
        UserSession session = applicationContext.getBean(UserSession.class);

        if (session.getUser().getUserType().eqVENDOR()) {
            Map<String, Object> map = new HashMap<>();

            map.put("serviceId", serviceId);
            String sql = "SELECT * FROM service WHERE id=:serviceId";
            String sql1 = "SELECT * FROM dladle.public.service_documents WHERE service_id=:serviceId";

            List<ServiceDocuments> documents = new ArrayList<>();
            this.jdbcTemplate.query(sql1, map, (rs, rowNum) -> {
                        ServiceDocuments s = new ServiceDocuments();
                        s.setBase64(rs.getString("url"));
                        s.setDocumentType(DocumentTypeMapper.getDocumentType(rs.getInt("document_type")));
                        documents.add(s);
                        return s;
                    }
            );
            return this.jdbcTemplate.queryForObject(sql, map, (rs, rowNum) -> {
                ServiceView serviceView = new ServiceView();
                serviceView.setEmergency(rs.getBoolean("emergency"));
                serviceView.setServiceDescription(rs.getString("service_description"));
                serviceView.setServiceDocuments(documents);
                serviceView.setServiceNeedTime(rs.getString("service_need_time"));
                return serviceView;
            });

        } else {
            throw new Exception("You are not authorised to use this");
        }
    }

    public void estimateWork(VendorEstimate vendorEstimate) throws UserNotFoundException {

        UserSession session = applicationContext.getBean(UserSession.class);

        Long userId = userUtility.findVendorIdByEmail(session.getUser().getEmailId());

        Map<String, Object> map = new HashMap<>();

        map.put("vendorId", userId);
        map.put("serviceId", vendorEstimate.getServiceId());
        map.put("feeStartRange", vendorEstimate.getFeeStartRange());
        map.put("feeEndRange", vendorEstimate.getFeeEndRange());

        String sql1 = "UPDATE dladle.public.service_estimations SET fee_end_range=:feeEndRange,fee_start_range=:feeStartRange WHERE vendor_id=:vendorId AND service_id=:serviceId ";
        this.jdbcTemplate.update(sql1, map);
    }

    public VendorResponse findRightVendor(Long serviceId) throws Exception {
        VendorRequest vendorRequest = new VendorRequest();

        Map<String, Object> map = new HashMap<>();
        map.put("serviceId", serviceId);
        String sql1 = "SELECT emergency FROM service WHERE id=:serviceId";
        Boolean emergency = this.jdbcTemplate.queryForObject(sql1, map, boolean.class);
        vendorRequest.setEmergency(emergency);
        List<Vendor> vendors = new ArrayList<>();
        String sql = "SELECT * FROM service_estimations WHERE service_id=:serviceId";
        this.jdbcTemplate.query(sql, map, (rs -> {
            Vendor vendor = new Vendor();
            vendor.setExperience(rs.getString("experience"));
            vendor.setFeeEndRange(rs.getDouble("fee_end_range"));
            vendor.setFeeStartRange(rs.getDouble("fee_start_range"));
            vendor.setProximity(rs.getDouble("proximity"));
            vendor.setRating(rs.getDouble("rating"));
            vendor.setVendorId(rs.getLong("vendor_id"));
            vendors.add(vendor);
            vendorRequest.setVendors(vendors);
        }));

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map1 = restTemplate.postForObject(url, vendorRequest, Map.class);
        Map<String, Object> m = (Map<String, Object>) map1.get("Data");
        if (m != null) {
            VendorResponse vendorResponse = new VendorResponse();
            vendorResponse.setVendorId(Long.valueOf(m.get("vendorId").toString()));
            vendorResponse.setWeighted(Double.valueOf(m.get("weighted").toString()));
            return vendorResponse;
        } else {
            throw new Exception("Unable to access Vendor Selection Engine");
        }
    }
}
