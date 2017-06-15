package za.co.dladle.entity;

import za.co.dladle.model.NotificationType;

/**
 * Created by prady on 6/7/2017.
 */
public class NotificationView {
    private String from;
    private String to;
    private String title;
    private String body;
    private String data;
    private String imageUrl;
    private long houseId;
    private NotificationType notificationType;

    public NotificationView() {
    }

    public NotificationView(String from, String to, String title, String body, String data, String imageUrl, long houseId, NotificationType notificationType) {
        this.from = from;
        this.to = to;
        this.title = title;
        this.body = body;
        this.data = data;
        this.imageUrl = imageUrl;
        this.houseId = houseId;
        this.notificationType = notificationType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
