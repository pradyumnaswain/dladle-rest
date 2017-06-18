package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created by prady on 3/24/2017.
 */
public class NotificationServiceJavaMailImpl implements NotificationService {
    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationServiceJavaMailImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("pradyumnaswain76@gmail.com");
        simpleMailMessage.setTo("pradyumnaswain76@gmail.com");
        simpleMailMessage.setSubject("Hello");
        simpleMailMessage.setText("Hello");

        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendWelcomeMail(String toEmailId) {

    }

    @Override
    public void sendOtpMail(String toEmailId, int otp) {

    }

    @Override
    public void sendVerificationMail(String toEmailId, String verifyLink) {

    }

    @Override
    public void sendPropertyInviteMail(String toEmailId) {

    }

    @Override
    public void sendPropertyRequesteMail(String toEmailId) {

    }

    @Override
    public void sendPropertyDeclineMail(String emailId) {

    }

}
