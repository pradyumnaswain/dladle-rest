package za.co.dladle.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.RowMapper;
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
import za.co.dladle.model.DocumentType;
import za.co.dladle.model.NotificationType;
import za.co.dladle.model.Property;
import za.co.dladle.model.ServiceStatus;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.thirdparty.AndroidPushNotificationsService;
import za.co.dladle.thirdparty.FileManagementServiceCloudinaryImpl;
import za.co.dladle.thirdparty.NotificationService;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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
    private RatingService ratingService;

    @Autowired
    private FileManagementServiceCloudinaryImpl fileManagementServiceCloudinary;

    @Autowired
    private PushNotificationService notificationService;

    @Autowired
    private AndroidPushNotificationsService pushNotificationsService;

    @Value("${vendor.selection.engine}")
    private String url;

    @Value("${vendor.selection.max.distance}")
    private String distance;

    public long requestVendor(VendorServiceRequest vendorServiceRequest) throws Exception {

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

        if (vendorServiceRequest.getServiceDocuments() != null &&
                !vendorServiceRequest.getServiceDocuments().isEmpty() &&
                vendorServiceRequest.getServiceDocuments().size() > 0) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (ServiceDocuments file : vendorServiceRequest.getServiceDocuments()) {
                if (file.getDocumentType().equals(DocumentType.IMAGE)) {
                    completionService.submit(() -> {
                        Map<String, Object> map = new HashMap<>();

                        String imageUrl = fileManagementServiceCloudinary.upload(file.getBase64(), file.getFileName());

                        map.put("serviceId", keyHolder.getKey().longValue());
                        map.put("imageUrl", imageUrl);
                        map.put("documentType", DocumentTypeMapper.getDocumentType(file.getDocumentType()));

                        list.add(map);
                        return null;
                    });
                } else {
                    completionService.submit(() -> {
                        Map<String, Object> map = new HashMap<>();

                        String imageUrl = fileManagementServiceCloudinary.uploadAudio(file.getBase64(), file.getFileName());

                        map.put("serviceId", keyHolder.getKey().longValue());
                        map.put("imageUrl", imageUrl);
                        map.put("documentType", DocumentTypeMapper.getDocumentType(file.getDocumentType()));

                        list.add(map);
                        return null;
                    });
                }
            }
            for (ServiceDocuments file : vendorServiceRequest.getServiceDocuments()) {
                completionService.take().get();
            }

            String sql1 = "INSERT INTO service_documents (service_id, url,document_type) VALUES (:serviceId,:imageUrl,:documentType)";
            this.jdbcTemplate.batchUpdate(sql1, list.toArray(new Map[vendorServiceRequest.getServiceDocuments().size()]));
        }
        List<VendorAtWorkView> vendorsAtWork = getVendorsAtWork(ServiceTypeMapper.getServiceType(vendorServiceRequest.getServiceType()));

        String sql2 = "SELECT * FROM property INNER JOIN house ON property.id =house.property_id WHERE house.id=:houseId";

        Property propery = jdbcTemplate.queryForObject(sql2, mapSqlParameterSource, new RowMapper<Property>() {

            @Override
            public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
                Property property = new Property();
                property.setAddressLatitude(rs.getString("address_latitude"));
                property.setAddressLongitude(rs.getString("address_longitude"));
                return property;
            }
        });
        vendorsAtWork = getNearestVendors(vendorsAtWork, propery.getAddressLatitude(), propery.getAddressLongitude());

        if (!vendorsAtWork.isEmpty()) {
            for (VendorAtWorkView vendorAtWorkView : vendorsAtWork) {
                completionService.submit(() -> {
                    mapSqlParameterSource.addValue("vendorId", vendorAtWorkView.getVendorId());
                    String sql1 = "SELECT * FROM user_dladle INNER JOIN vendor ON user_dladle.id = vendor.user_id WHERE vendor.id=:vendorId";
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
        return keyHolder.getKey().longValue();
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
                    rs.getString("current_location_latitude"), rs.getString("current_location_longitude"), rs.getString("name"), rs.getDouble("value"));
            vendorAtWorkViews.add(vendorAtWorkView);
            return vendorAtWorkView;

        });
        return vendorAtWorkViews;
    }

    public List<VendorAtWorkView> getNearestVendors(List<VendorAtWorkView> vendorAtWorkViews, String
            latitude, String longitude) throws InterruptedException, ApiException, IOException {
        GeoApiContext geoApiContext = new GeoApiContext();
        String destinitions = "";
        geoApiContext.setApiKey("AIzaSyBBD8kFX9-dZqyXoNs6KsgiuKlhSGkvU28");
        for (VendorAtWorkView vendorAtWorkView : vendorAtWorkViews) {
            destinitions = destinitions + vendorAtWorkView.getCurrentLocationLatitude() + "," + vendorAtWorkView.getCurrentLocationLongitude() + "|";
        }

        DistanceMatrix distanceMatrix = DistanceMatrixApi.newRequest(geoApiContext)
                .origins(latitude + "," + longitude)
                .destinations(destinitions)
                .mode(TravelMode.DRIVING).
                        await();

        int i = 0;
        List<Double> distLst = new ArrayList<Double>();

        for (DistanceMatrixRow row : distanceMatrix.rows) {
            for (DistanceMatrixElement element : row.elements) {
//                distLst.add(Double.parseDouble(element.distance.humanReadable));
                distLst.add(Double.parseDouble("10"));
            }

        }
        List<VendorAtWorkView> views = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(distLst)) {
            for (double dist : distLst) {
                if (dist <= Double.parseDouble(distance)) {
                    views.add(vendorAtWorkViews.get(i));
                }
                i++;

            }
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
        map.put("locationLatitude", vendorOnWork.getCurrentLocationLatitude());
        map.put("locationLongitude", vendorOnWork.getCurrentLocationLongitude());

        String sql = "SELECT count(*) FROM vendor_work_timeline WHERE vendor_id=:vendorId AND current_work_status=TRUE ";
        Integer count = this.jdbcTemplate.queryForObject(sql, map, Integer.class);
        if (count == 0) {
            String sql1 = "INSERT INTO vendor_work_timeline(vendor_id, current_work_status, on_work_from, current_location_latitude,current_location_longitude) VALUES " +
                    "(:vendorId,TRUE ,:time,:locationLatitude,:locationLongitude)";
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
            String sql1 = "SELECT * FROM service_documents WHERE service_id=:serviceId";

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

        String sql1 = "UPDATE service_estimations SET fee_end_range=:feeEndRange,fee_start_range=:feeStartRange WHERE vendor_id=:vendorId AND service_id=:serviceId ";
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
//        List<ServiceInstance> instances = discoveryClient.getInstances("dladle-vendor-selection-engine");
//        ServiceInstance serviceInstance = instances.get(0);
//        String baseUrl = serviceInstance.getUri().toString();
//        baseUrl = baseUrl + "/api/vendor/engine/selection";
//        Map<String, Object> map1 = restTemplate.postForObject(baseUrl, vendorRequest, Map.class);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map1 = restTemplate.postForObject(url, vendorRequest, Map.class);
        Map<String, Object> m = (Map<String, Object>) map1.get("Data");
        if (m != null) {
            VendorResponse vendorResponse;

            map.put("vendorId", Long.valueOf(m.get("vendorId").toString()));
            String sqlVendor = "SELECT user_dladle.*,service_type.name job_type,years_exp.name years_of_exp FROM user_dladle INNER JOIN vendor ON user_dladle.id = vendor.user_id " +
                    "INNER JOIN service_type ON vendor.service_type_id = service_type.id " +
                    "INNER JOIN years_exp ON vendor.experience_id = years_exp.id " +
                    "WHERE vendor.id=:vendorId";

            vendorResponse = this.jdbcTemplate.queryForObject(sqlVendor, map, (rs, rowNum) ->
                    new VendorResponse(Long.valueOf(m.get("vendorId").toString()),
                            rs.getString("emailId"),
                            rs.getString("first_name") + " " + rs.getString("last_name"),
                            rs.getString("profile_picture"), rs.getString("job_type"),
                            rs.getString("years_of_exp")));

            vendorResponse.setVendorRating(ratingService.viewRating(vendorResponse.getVendorEmailId()));
            Vendor vendor = vendors.stream().filter(x -> Objects.equals(x.getVendorId(), Long.valueOf(m.get("vendorId").toString()))).collect(Collectors.toList()).get(0);
            vendorResponse.setProposedFeeStartRange(vendor.getFeeStartRange());
            vendorResponse.setProposedFeeEndRange(vendor.getFeeEndRange());

            String sqlNumberOfJobs = "SELECT count(*) FROM service WHERE vendor_id=:vendorId";
            Integer numberOfJobs = this.jdbcTemplate.queryForObject(sqlNumberOfJobs, map, Integer.class);

            vendorResponse.setNumberOfJobs(numberOfJobs);

            return vendorResponse;
        } else {
            throw new Exception("Unable to access Vendor Selection Engine");
        }
    }
}
