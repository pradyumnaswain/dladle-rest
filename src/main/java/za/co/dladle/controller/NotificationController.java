package za.co.dladle.controller;

import com.sendgrid.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.service.NotificationServiceSendGridImpl;

import java.io.IOException;

/**
 * Created by prady on 7/26/2016.
 */
@RestController
public class NotificationController {
    @Autowired
    private NotificationServiceSendGridImpl notificationServiceSendGridImpl;

    @RequestMapping(value = "/send-email", method = RequestMethod.POST)
    public String sendMail(@RequestParam String emailId) throws IOException {
        Response response = notificationServiceSendGridImpl.sendVerificationMail(emailId, "");

        return response.body;
    }
}
