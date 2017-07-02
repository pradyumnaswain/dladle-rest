package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.*;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.mapper.DocumentTypeMapper;
import za.co.dladle.mapper.ServiceStatusMapper;
import za.co.dladle.mapper.ServiceTypeMapper;
import za.co.dladle.model.ServiceStatus;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.thirdparty.FileManagementServiceCloudinaryImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public void requestVendor(VendorServiceRequest vendorServiceRequest) throws UserNotFoundException, IOException {

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
                .addValue("needTime", vendorServiceRequest.getServiceNeedTime());

        KeyHolder keyHolder = new GeneratedKeyHolder();


        String sql = "INSERT INTO service(service_type_id, service_request_time, service_requester_user_id, service_status_id, service_need_time, emergency, service_description,house_id) " +
                "VALUES (:serviceType,:serviceRequestTime,:requestUserId,:serviceStatus,:needTime,:emergency,:description,:houseId)";

        this.jdbcTemplate.update(sql, mapSqlParameterSource, keyHolder, new String[]{"id"});

        if (vendorServiceRequest.getServiceDocuments() != null) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (ServiceDocuments file : vendorServiceRequest.getServiceDocuments()) {
                Map<String, Object> map = new HashMap<>();

                String imageUrl = fileManagementServiceCloudinary.upload(file.getBase64());

                map.put("serviceId", keyHolder.getKey().longValue());
                map.put("imageUrl", imageUrl);
                map.put("documentType", DocumentTypeMapper.getDocumentType(file.getDocumentType()));

                list.add(map);
            }
            String sql1 = "INSERT INTO service_documents (service_id, url,document_type) VALUES (:serviceId,:imageUrl,:documentType)";
            this.jdbcTemplate.batchUpdate(sql1, list.toArray(new Map[vendorServiceRequest.getServiceDocuments().size()]));
        }
        // TODO: 7/2/2017 Find Nearest Vendors and populate service_estimations and send notifications

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

    public void viewWork(Long serviceId) {
        UserSession session = applicationContext.getBean(UserSession.class);

        if (session.getUser().getUserType().eqVENDOR()) {
            Map<String, Object> map = new HashMap<>();

            map.put("serviceId", serviceId);
            String sql = "SELECT * FROM service WHERE id=:serviceId";
            this.jdbcTemplate.queryForObject(sql, map, (rs, rowNum) -> new ServiceView()
            );

        }
    }

    public void estimateWork(VendorEstimate vendorEstimate) {
        // TODO: 7/2/2017 update service estimations
    }
}
