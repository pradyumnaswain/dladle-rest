package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.service.NotificationService;

import java.util.Date;

/**
 * Created by prady on 7/26/2016.
 */
@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/send-email", method = RequestMethod.GET)
    public String sendNotifcation() {
        notificationService.sendNotification();

        Date date = new Date();

        System.out.println(date);

        return "Thank You";
    }
}
