package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by prady on 7/26/2016.
 */
@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("pradyumnaswain76@gmail.com");
        simpleMailMessage.setTo("pradyumnaswain76@gmail.com");
        simpleMailMessage.setSubject("Hello");
        simpleMailMessage.setText("Hello");

        javaMailSender.send(simpleMailMessage);
    }
}
