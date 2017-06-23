package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.entity.NotificationView;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.mapper.NotificationTypeMapper;
import za.co.dladle.model.Notification;
import za.co.dladle.model.NotificationType;
import za.co.dladle.session.UserSession;
import za.co.dladle.util.UserUtility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/7/2017.
 */
@Service
public class PushNotificationService {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserUtility userUtility;

    @Autowired
    private RatingService ratingService;

    public List<Notification> listNotifications() throws UserNotFoundException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());
        Map<String, Object> map = new HashMap<>();
        List<Notification> notificationList = new ArrayList<>();
        map.put("userId", userId);
        String sql = "SELECT notification.*,u.*,u.emailid fromId, p.emailid toId,notification_type.id not_type_id, notification_type.name FROM notification " +
                "INNER JOIN notification_type ON notification.notification_type_id = notification_type.id " +
                "INNER JOIN user_dladle u ON notification_from=u.id " +
                "INNER JOIN user_dladle p ON notification_to=p.id " +
                "WHERE notification_to=:userId";
        this.jdbcTemplate.query(sql, map, (rs1, rowNum1) -> {
            Notification notification = new Notification();
            notification.setId(rs1.getLong("id"));
            notification.setFrom(rs1.getString("fromId"));
            notification.setName(rs1.getString("first_name") + " " + rs1.getString("last_name"));
            notification.setProfilePicture(rs1.getString("profile_picture"));
            try {
                Double rating = ratingService.viewRating(notification.getFrom());
                notification.setRating(rating);
            } catch (Exception e) {
                notification.setRating(0D);
                e.printStackTrace();
            }
            notification.setTo(rs1.getString("toId"));
            notification.setData(rs1.getString("notification_data"));
            notification.setTitle(rs1.getString("notification_title"));
            notification.setBody(rs1.getString("notification_body"));
            notification.setTime(rs1.getString("notification_time"));
            notification.setImageUrl(rs1.getString("notification_image_url"));
            notification.setRead(rs1.getBoolean("notification_read_status"));
            notification.setActioned(rs1.getBoolean("notification_actioned_status"));
            notification.setHouseId(rs1.getLong("house_id"));
            notification.setNotificationType(rs1.getString("name"));
            notification.setNotificationTypeId(rs1.getLong("not_type_id"));
            notificationList.add(notification);
            return notification;
        });

        return notificationList;

    }

    public void readNotifications(long notificationId, boolean read) {

        Map<String, Object> map = new HashMap<>();
        map.put("id", notificationId);
        map.put("read", read);
        String sql = "UPDATE notification SET notification_read_status=:read WHERE id=:id";
        this.jdbcTemplate.update(sql, map);
    }

    void saveNotification(NotificationView notification) throws UserNotFoundException {
        Map<String, Object> map = new HashMap<>();
        map.put("title", notification.getTitle());
        map.put("from", userUtility.findUserIdByEmail(notification.getFrom()));
        map.put("to", userUtility.findUserIdByEmail(notification.getTo()));
        map.put("body", notification.getBody());
        map.put("data", notification.getData());
        map.put("imageUrl", notification.getImageUrl());
        map.put("time", LocalDate.now());
        map.put("read", Boolean.FALSE);
        map.put("actioned", Boolean.FALSE);
        if (notification.getHouseId() == null) {
            map.put("houseId", null);
        } else {
            map.put("houseId", Long.valueOf(notification.getHouseId()));
        }
        map.put("notificationTypeId", NotificationTypeMapper.getNotificationType(notification.getNotificationType()));


        String sql = "INSERT INTO notification(notification_from, notification_to, notification_title, notification_body, notification_data, notification_image_url, notification_time, notification_read_status, notification_actioned_status,house_id,notification_type_id) " +
                " VALUES (:from,:to,:title,:body,:data,:imageUrl,:time,:read,:actioned,:houseId,:notificationTypeId)";
        this.jdbcTemplate.update(sql, map);
    }

    void actionNotifications(Long tenantId, Long landlordId, NotificationType notificationTypeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("to", tenantId);
        map.put("from", landlordId);
        map.put("notificationTypeId", NotificationTypeMapper.getNotificationType(notificationTypeId));
        String sql = "UPDATE notification SET notification_actioned_status=TRUE WHERE notification_from=:from AND notification_to=:to AND notification_type_id=:notificationTypeId";
        this.jdbcTemplate.update(sql, map);
    }
}
