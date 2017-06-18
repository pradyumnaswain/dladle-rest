package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.model.Notification;
import za.co.dladle.service.PushNotificationService;
import za.co.dladle.util.ApiConstants;
import za.co.dladle.util.DladleConstants;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/7/2017.
 */
@RestController
public class NotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    //------------------------------------------------------------------------------------------------------------------
    //Read Notification
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.NOTIFICATION_READ, method = RequestMethod.POST)
    public Map<String, Object> readNotifications(@RequestParam long notificationId, @RequestParam boolean read) throws IOException {
        try {
            pushNotificationService.readNotifications(notificationId, read);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.NOTIFICATION_READ);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Notifications
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.NOTIFICATION_LIST, method = RequestMethod.GET)
    public Map<String, Object> listNotification() throws IOException {
        try {
            List<Notification> notificationList = pushNotificationService.listNotifications();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, notificationList, DladleConstants.NOTIFICATION_LISTED);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

}
