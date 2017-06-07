package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.Notification;
import za.co.dladle.session.UserSession;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private UserServiceUtility userServiceUtility;

    public List<Notification> listNotifications() throws UserNotFoundException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        Long userId = userServiceUtility.findUserIdByEmail(userSession.getUser().getEmailId());
        Map<String, Object> map = new HashMap<>();
        List<Notification> notificationList = new ArrayList<>();
        map.put("userId", userId);
        String sql = "SELECT notification.*,u.emailid toId, p.emailid fromId FROM notification INNER JOIN user_dladle u ON notification_from=u.id INNER JOIN user_dladle p ON notification_to=p.id WHERE (notification_from=:userId OR notification_to=:userId)";
        this.jdbcTemplate.query(sql, map, (rs1, rowNum1) -> {
            Notification notification = new Notification();
            notification.setId(rs1.getLong("id"));
            notification.setFrom(rs1.getString("fromId"));
            notification.setTo(rs1.getString("toId"));
            notification.setData(rs1.getString("notification_data"));
            notification.setTitle(rs1.getString("notification_title"));
            notification.setBody(rs1.getString("notification_body"));
            notification.setTime(rs1.getString("notification_time"));
            notification.setImageUrl(rs1.getString("notification_image_url"));
            notification.setRead(rs1.getBoolean("notification_read_status"));
            notification.setActioned(rs1.getBoolean("notification_actioned_status"));
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

    public void actionNotifications(long notificationId, boolean actioned) {

        Map<String, Object> map = new HashMap<>();
        map.put("id", notificationId);
        map.put("actioned", actioned);
        String sql = "UPDATE notification SET notification_actioned_status=:actioned WHERE id=:id";
        this.jdbcTemplate.update(sql, map);
    }

    void saveNotification(Notification notification) throws UserNotFoundException {
        Map<String, Object> map = new HashMap<>();
        map.put("title", notification.getTitle());
        map.put("from", userServiceUtility.findUserIdByEmail(notification.getFrom()));
        map.put("to", userServiceUtility.findUserIdByEmail(notification.getTo()));
        map.put("body", notification.getBody());
        map.put("data", notification.getData());
        map.put("imageUrl", notification.getImageUrl());
        map.put("time", LocalDateTime.now(ZoneId.systemDefault()));
        map.put("read", Boolean.FALSE);
        map.put("actioned", Boolean.FALSE);

        String sql = "INSERT INTO notification(notification_from, notification_to, notification_title, notification_body, notification_data, notification_image_url, notification_time, notification_read_status, notification_actioned_status) " +
                " VALUES (:from,:to,:title,:body,:data,:imageUrl,:time,:read,:actioned)";
        this.jdbcTemplate.update(sql, map);
    }

    void actionNotifications(Integer tenantId, Integer landlordId, Boolean aTrue) {
        Map<String, Object> map = new HashMap<>();
        map.put("to", tenantId);
        map.put("from", landlordId);
        map.put("actioned", aTrue);
        String sql = "UPDATE notification SET notification_actioned_status=:actioned WHERE notification_from=:from AND notification_to=:to";
        this.jdbcTemplate.update(sql, map);

    }
}
