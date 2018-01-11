package za.co.dladle.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONException;
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
import za.co.dladle.mapper.RejectionReasonMapper;
import za.co.dladle.mapper.ServiceStatusMapper;
import za.co.dladle.mapper.ServiceTypeMapper;
import za.co.dladle.model.*;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.thirdparty.document.DocumentManagementServiceImpl;
import za.co.dladle.thirdparty.push.AndroidPushNotificationsService;
import za.co.dladle.thirdparty.document.DocumentManagementServiceCloudinaryImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private DocumentManagementServiceImpl fileManagementServiceCloudinary;

    @Autowired
    private PushNotificationService notificationService;

    @Autowired
    private AndroidPushNotificationsService pushNotificationsService;

    @Value("${vendor.selection.engine}")
    private String url;

    @Value("${vendor.selection.max.distance}")
    private String distance;

    @Value("${document.store.url}")
    private String documentUrl;


    public long requestVendor(VendorServiceRequest vendorServiceRequest) throws Exception {

//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
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
                    Map<String, Object> map = new HashMap<>();

                    String imageUrl = fileManagementServiceCloudinary.uploadPhoto(String.valueOf(keyHolder.getKey().longValue()), "service", file.getBase64(), file.getFileName());

                    map.put("serviceId", keyHolder.getKey().longValue());
                    map.put("imageUrl", imageUrl);
                    map.put("documentType", DocumentTypeMapper.getDocumentType(file.getDocumentType()));

                    list.add(map);
                } else {
                    Map<String, Object> map = new HashMap<>();

                    String imageUrl = fileManagementServiceCloudinary.uploadAudio(String.valueOf(keyHolder.getKey().longValue()), "service", file.getBase64(), file.getFileName());

                    map.put("serviceId", keyHolder.getKey().longValue());
                    map.put("imageUrl", imageUrl);
                    map.put("documentType", DocumentTypeMapper.getDocumentType(file.getDocumentType()));

                    list.add(map);
                }
            }

            String sql1 = "INSERT INTO service_documents (service_id, url,document_type) VALUES (:serviceId,:imageUrl,:documentType)";
            this.jdbcTemplate.batchUpdate(sql1, list.toArray(new Map[vendorServiceRequest.getServiceDocuments().size()]));
        }
        List<VendorAtWorkView> vendorsAtWork = getVendorsAtWork(ServiceTypeMapper.getServiceType(vendorServiceRequest.getServiceType()), session.getUser().getEmailId());

        String sql2 = "SELECT * FROM property INNER JOIN house ON property.id =house.property_id WHERE house.id=:houseId";

        Property propery = jdbcTemplate.queryForObject(sql2, mapSqlParameterSource, (rs, rowNum) -> {
            Property property = new Property();
            property.setAddressLatitude(rs.getString("address_latitude"));
            property.setAddressLongitude(rs.getString("address_longitude"));
            return property;
        });
        vendorsAtWork = getNearestVendors(vendorsAtWork, propery.getAddressLatitude(), propery.getAddressLongitude());

        if (!vendorsAtWork.isEmpty()) {
            for (VendorAtWorkView vendorAtWorkView : vendorsAtWork) {
//                completionService.submit(() -> {
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
//                    return null;
//                });
//                for (int i = 0; i < vendorsAtWork.size(); i++) {
//                    completionService.take().get();
//                    // Some processing here
//                }

            }
            populateVendorWorkTimeline(vendorsAtWork, keyHolder.getKey().longValue());
        } else {
            throw new Exception("Currently No vendor at Work");
        }
        return keyHolder.getKey().longValue();
    }

    private List<VendorAtWorkView> getVendorsAtWork(Integer serviceType, String emailId) {
        Map<String, Object> map = new HashMap<>();
        map.put("serviceType", serviceType);
        List<VendorAtWorkView> vendorAtWorkViews = new ArrayList<>();
        String sql = "SELECT * FROM vendor_work_timeline " +
                "INNER JOIN vendor ON vendor_work_timeline.vendor_id = vendor.id " +
                "INNER JOIN years_exp ON vendor.experience_id = years_exp.id " +
                "INNER JOIN user_dladle ON vendor.user_id = user_dladle.id " +
                "WHERE current_work_status=TRUE AND service_type_id=:serviceType";
        this.jdbcTemplate.query(sql, map, (rs, rowNum) -> {
            VendorAtWorkView vendorAtWorkView = new VendorAtWorkView(rs.getLong("vendor_id"),
                    rs.getString("current_location_latitude"), rs.getString("current_location_longitude"), rs.getString("name"));
            try {
                vendorAtWorkView.setRating(ratingService.viewRating(emailId));
            } catch (Exception e) {
                vendorAtWorkView.setRating(0D);
                e.printStackTrace();
            }
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
                if (element.distance != null) {
                    distLst.add(Double.parseDouble(String.valueOf(element.distance.inMeters)));
                } else {
                    distLst.add(0D);
                }
            }

        }
        List<VendorAtWorkView> views = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(distLst)) {
            for (double dist : distLst) {
                if (dist <= Double.parseDouble(distance)) {
                    VendorAtWorkView vendorAtWorkView = vendorAtWorkViews.get(i);
                    vendorAtWorkView.setProximity(dist);
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
            map.put("notificationSentTime", LocalDateTime.now());

            list.add(map);
        }
        String sql1 = "INSERT INTO service_estimations (service_id, vendor_id, experience, proximity, rating,notification_sent_time) VALUES (:serviceId,:vendorId,:experience,:proximity,:rating,:notificationSentTime)";
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

            Long vendorId = userUtility.findVendorIdByEmail(session.getUser().getEmailId());
            map.put("serviceId", serviceId);
            map.put("vendorId", vendorId);
            String sql = "SELECT * FROM service INNER JOIN house h2 ON service.house_id = h2.id " +
                    "INNER JOIN property p ON h2.property_id = p.id  " +
                    " WHERE service.id=:serviceId";
            String sql1 = "SELECT * FROM service_documents WHERE service_id=:serviceId";

            String sqlEstimate = "SELECT * FROM service_estimations WHERE service_id=:serviceId AND vendor_id=:vendorId";
            ServiceEstimateView serviceEstimateView = this.jdbcTemplate.queryForObject(sqlEstimate, map, (rs, rowNum) -> new ServiceEstimateView(rs.getString("fee_start_range"), rs.getString("fee_end_range")));

            List<ServiceDocuments> documents = new ArrayList<>();
            this.jdbcTemplate.query(sql1, map, (rs, rowNum) -> {
                        ServiceDocuments s = new ServiceDocuments();
                        s.setBase64(documentUrl + "service/" + serviceId + "/" + rs.getString("url"));
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
                serviceView.setPropertyAddress(rs.getString("address"));
                try {
                    User user = userUtility.findUserById(rs.getLong("service_requester_user_id"));

                    Double rating = ratingService.viewRating(user.getEmailId());

                    UserView userView = new UserView(user.getFirstName() + " " + user.getLastName(), rating, user.getProfilePicture());

                    serviceView.setUser(userView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                serviceView.setServiceEstimateView(serviceEstimateView);
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
        map.put("estimationResponseTime", LocalDateTime.now());

        String sql1 = "UPDATE service_estimations SET fee_end_range=:feeEndRange,fee_start_range=:feeStartRange,estimation_response_time=:estimationResponseTime WHERE vendor_id=:vendorId AND service_id=:serviceId ";
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
        String sql = "SELECT * FROM service_estimations WHERE service_id=:serviceId AND fee_start_range IS NOT NULL AND fee_end_range IS NOT NULL ";
        this.jdbcTemplate.query(sql, map, (rs -> {
            Vendor vendor = new Vendor();
            vendor.setExperience(rs.getString("experience"));
            vendor.setFeeEndRange(rs.getDouble("fee_end_range"));
            vendor.setFeeStartRange(rs.getDouble("fee_start_range"));
            vendor.setProximity(rs.getDouble("proximity"));
            vendor.setRating(rs.getDouble("rating"));
            vendor.setVendorId(rs.getLong("vendor_id"));
            LocalDateTime notificationSentTime = rs.getTimestamp("notification_sent_time").toLocalDateTime();

            LocalDateTime estimationResponseTime = rs.getTimestamp("estimation_response_time") != null ? rs.getTimestamp("estimation_response_time").toLocalDateTime() : null;

            Long timeDifference = null;
            if (estimationResponseTime != null) {
                timeDifference = estimationResponseTime.until(notificationSentTime, ChronoUnit.MILLIS);
            }
            vendor.setmRequestTime(timeDifference);
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

    public void estimateFinalPrice(EstimateFinalPrice estimateFinalPrice) throws UserNotFoundException, JSONException {
        UserSession userSession = applicationContext.getBean(UserSession.class);
        Long vendorId = userUtility.findVendorIdByEmail(userSession.getUser().getEmailId());

        Map<String, Object> map = new HashMap<>();
        map.put("vendorId", vendorId);
        map.put("serviceId", estimateFinalPrice.getServiceId());
        map.put("finalPrice", estimateFinalPrice.getFinalPrice());

        String sqlFindUserId = "SELECT service_requester_user_id FROM service WHERE id=:serviceId";

        Long userId = this.jdbcTemplate.queryForObject(sqlFindUserId, map, Long.class);

        User user = userUtility.findUserById(userId);

        String sql = "UPDATE service SET service_fee=:finalPrice WHERE id=:serviceId AND vendor_id=:vendorId";
        this.jdbcTemplate.update(sql, map);

        NotificationView notifications = new NotificationView(
                userSession.getUser().getEmailId(),
                user.getEmailId(),
                NotificationConstants.SERVICE_FINAL_ESTIMATION_TITLE,
                NotificationConstants.SERVICE_FINAL_ESTIMATION_BODY,
                "serviceId:" + estimateFinalPrice.getServiceId() + "," + "vendorEmailId:" + userSession.getUser().getEmailId() + "," + "vendorFirstName:" + userSession.getUser().getFirstName() + "," + "vendorLastName:" + userSession.getUser().getLastName() + "," + "vendorProfilePicture:" + userSession.getUser().getProfilePicture() + "," + "finalFee:" + estimateFinalPrice.getFinalPrice(),
                "", "0", NotificationType.SERVICE_FINAL_PRICE_ESTIMATED);
        notificationService.saveNotification(notifications);
//        emailService.sendNotificationMail(propertyAssignmentRequest.getEmailId(), NotificationConstants.PROPERTY_ACCEPTED_TITLE, NotificationConstants.PROPERTY_ACCEPTED_BODY);

        String sql11 = "UPDATE service SET vendor_id=:vendorId WHERE id=:serviceId";
        this.jdbcTemplate.update(sql11, map);
        map.put("emailId", user.getEmailId());
        String sqlDevice = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";
        String deviceId = this.jdbcTemplate.queryForObject(sqlDevice, map, String.class);

        //Send Push Notification
        if (deviceId != null) {
            JSONObject body = new JSONObject();
            body.put("to", deviceId);
            body.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", NotificationConstants.SERVICE_FINAL_ESTIMATION_TITLE);
            notification.put("body", NotificationConstants.SERVICE_FINAL_ESTIMATION_BODY);

            JSONObject data = new JSONObject();
            body.put("notification", notification);
            body.put("data", data);
            pushNotificationsService.sendNotification(body);
        } else {
            System.out.println("Device Id can't be null");
        }


    }

    public void acceptVendor(AcceptRequest acceptRequest) throws UserNotFoundException, JSONException {
        UserSession userSession = applicationContext.getBean(UserSession.class);
        Map<String, Object> map = new HashMap<>();
        map.put("vendorId", userUtility.findVendorIdByEmail(acceptRequest.getVendorEmailId()));
        map.put("serviceId", acceptRequest.getServiceId());

        String sqlServiceEstimation = "SELECT * FROM service_estimations WHERE service_id=:serviceId AND vendor_id=:vendorId";

        VendorPriceDetails vendorPriceDetails = this.jdbcTemplate.queryForObject(sqlServiceEstimation, map, (rs, rowNum) -> new VendorPriceDetails(
                rs.getString("fee_start_range"), rs.getString("fee_end_range")
        ));

        NotificationView notifications = new NotificationView(
                userSession.getUser().getEmailId(),
                acceptRequest.getVendorEmailId(),
                NotificationConstants.SERVICE_REQUEST_ACCEPT_TITLE,
                NotificationConstants.SERVICE_REQUEST_ACCEPT_BODY,
                "serviceId:" + acceptRequest.getServiceId() + "," + "vendorEmailId:" + acceptRequest.getVendorEmailId() + "," + "feeStartRange:" + vendorPriceDetails.getFeeEndRange() + "," + "feeEndRange:" + vendorPriceDetails.getFeeEndRange(),
                "", "0", NotificationType.SERVICE_REQUEST_ACCEPT);
        notificationService.saveNotification(notifications);
//        emailService.sendNotificationMail(propertyAssignmentRequest.getEmailId(), NotificationConstants.PROPERTY_ACCEPTED_TITLE, NotificationConstants.PROPERTY_ACCEPTED_BODY);

        String sql = "UPDATE service SET vendor_id=:vendorId WHERE id=:serviceId";
        this.jdbcTemplate.update(sql, map);
        map.put("emailId", acceptRequest.getVendorEmailId());
        String sqlDevice = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";
        String deviceId = this.jdbcTemplate.queryForObject(sqlDevice, map, String.class);

        //Send Push Notification
        if (deviceId != null) {
            JSONObject body = new JSONObject();
            body.put("to", deviceId);
            body.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", NotificationConstants.SERVICE_REQUEST_ACCEPT_TITLE);
            notification.put("body", NotificationConstants.SERVICE_REQUEST_ACCEPT_BODY);

            JSONObject data = new JSONObject();
            body.put("notification", notification);
            body.put("serviceId", acceptRequest.getServiceId());
            body.put("data", data);
            pushNotificationsService.sendNotification(body);
        } else {
            System.out.println("Device Id can't be null");
        }
    }

    public void rejectVendor(RejectRequest rejectRequest) throws UserNotFoundException, JSONException {
        UserSession userSession = applicationContext.getBean(UserSession.class);

        NotificationView notifications = new NotificationView(
                userSession.getUser().getEmailId(),
                rejectRequest.getVendorEmailId(),
                NotificationConstants.SERVICE_REQUEST_REJECT_TITLE,
                NotificationConstants.SERVICE_REQUEST_REJECT_BODY,
                "",
                "", "0", NotificationType.SERVICE_REQUEST_REJECTED);
        notificationService.saveNotification(notifications);
//        emailService.sendNotificationMail(propertyAssignmentRequest.getEmailId(), NotificationConstants.PROPERTY_ACCEPTED_TITLE, NotificationConstants.PROPERTY_ACCEPTED_BODY);

        Map<String, Object> map = new HashMap<>();
        map.put("vendorId", userUtility.findVendorIdByEmail(rejectRequest.getVendorEmailId()));
        map.put("serviceId", rejectRequest.getServiceId());
        map.put("rejectionId", RejectionReasonMapper.getRejectionReasonType(rejectRequest.getRejectionReason()));
        String sql = "UPDATE service SET vendor_id=:vendorId, vendror_rejection_reason_id=:rejectionId WHERE id=:serviceId";
        this.jdbcTemplate.update(sql, map);

        map.put("emailId", rejectRequest.getVendorEmailId());
        String sqlDevice = "SELECT device_id FROM user_dladle WHERE emailid=:emailId";
        String deviceId = this.jdbcTemplate.queryForObject(sqlDevice, map, String.class);

        //Send Push Notification
        if (deviceId != null) {
            JSONObject body = new JSONObject();
            body.put("to", deviceId);
            body.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", NotificationConstants.SERVICE_REQUEST_REJECT_TITLE);
            notification.put("body", NotificationConstants.SERVICE_REQUEST_REJECT_BODY);

            JSONObject data = new JSONObject();
            body.put("notification", notification);
            body.put("serviceId", rejectRequest.getServiceId());
            body.put("rejectionReason", RejectionReason.valueOf(rejectRequest.getRejectionReason().name()));
            body.put("data", data);
            pushNotificationsService.sendNotification(body);
        } else {
            System.out.println("Device Id can't be null");
        }
    }

    public void acceptVendorFinalPrice(AcceptRequest acceptRequest) {

    }

    public void rejectVendorFinalPrice(AcceptRequest acceptRequest) {

    }

    public ServiceCurrentStatus findServiceStatus(Long serviceId) {
        String sql = "SELECT * FROM service_estimations WHERE service_id=:serviceId";

        Map<String, Object> map = new HashMap<>();

        map.put("serviceId", serviceId);

        List<ServiceEstimateView> estimateViews = this.jdbcTemplate.query(sql, map, (rs, rowNum) -> new ServiceEstimateView(rs.getString("fee_start_range"), rs.getString("fee_end_range")));


        List<ServiceEstimateView> estimatedViews = estimateViews.stream().filter(serviceEstimateView -> serviceEstimateView.getFeeEndRange() != null).collect(Collectors.toList());

        String status;
        int statusType;

        if (estimateViews.size() == 0) {
            status = "No Near by Vendors available";
            statusType = 1;
        } else if (estimatedViews.size() == estimateViews.size()) {
            status = "All Near by Vendors Completed Quoting";
            statusType = 2;
        } else if (estimatedViews.size() == 0) {
            status = "Please Wait. All Near by Vendors Notified about your Service Request";
            statusType = 3;
        } else {
            status = "Please Wait. All Notified Vendors Started Quoting";
            statusType = 4;
        }

        return new ServiceCurrentStatus(estimateViews.size(), estimatedViews.size(), status, statusType);
    }
}
