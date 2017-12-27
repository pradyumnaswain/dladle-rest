package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
import za.co.dladle.model.Notification;
import za.co.dladle.service.PushNotificationService;
import za.co.dladle.serviceutil.ResponseUtil;

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
    //Read Notification
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.NOTIFICATION_ACTIONED, method = RequestMethod.GET)
    public Map<String, Object> actionNotification(@PathVariable long notificationId) throws IOException {
        try {
            pushNotificationService.actionNotifications(notificationId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.NOTIFICATION_ACTIONED);
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

    //------------------------------------------------------------------------------------------------------------------
    //List Notifications per HouseId
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.NOTIFICATION_LIST_HOUSE_ID, method = RequestMethod.GET)
    public Map<String, Object> listNotification(@PathVariable long houseId) throws IOException {
        try {
            List<Notification> notificationList = pushNotificationService.listNotifications(houseId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, notificationList, DladleConstants.NOTIFICATION_LISTED);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
