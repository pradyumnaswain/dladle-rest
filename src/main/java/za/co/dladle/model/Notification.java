package za.co.dladle.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by prady on 6/7/2017.
 */
public class Notification {
    private long id;
    private String from;
    private String to;
    private String title;
    private String body;
    private String data;
    private String imageUrl;
    private String time;
    private boolean read;
    private boolean actioned;
    private long houseId;

    public Notification() {
    }

    public Notification(String from, String to, String title, String body, String data, String imageUrl, long houseId) {
        this.from = from;
        this.to = to;
        this.title = title;
        this.body = body;
        this.data = data;
        this.imageUrl = imageUrl;
        this.houseId = houseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isActioned() {
        return actioned;
    }

    public void setActioned(boolean actioned) {
        this.actioned = actioned;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }
}
