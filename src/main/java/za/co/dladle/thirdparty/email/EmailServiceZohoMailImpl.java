package za.co.dladle.thirdparty.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import za.co.dladle.serviceutil.UserUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 10/1/2017.
 */
@Component
public class EmailServiceZohoMailImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Autowired
    private UserUtility userUtility;

    @Value("${spring.mail.username}")
    private String fromEmailId;

    @Value("${subject}")
    private String subject;

    @Override
    public void sendWelcomeMail(String toEmailId) {

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(fromEmailId);
            messageHelper.setTo(toEmailId);
            messageHelper.setSubject("Welcome to DlaDle, Welcome Home ");

            Map<String, Object> map = new HashMap<>();
            map.put("name", userUtility.getNameByEmailId(toEmailId));
            String content = mailContentBuilder.build("welcomeMailTemplate", map);
            messageHelper.setText(content, true);
        };
        javaMailSender.send(messagePreparator);
        System.out.println("Sent");
    }

    @Override
    public void sendOtpMail(String toEmailId, int otp) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(fromEmailId);
            messageHelper.setTo(toEmailId);
            messageHelper.setSubject(subject);

            Map<String, Object> map = new HashMap<>();
            map.put("otp", otp);
            String content = mailContentBuilder.build("otpMailTemplate", map);
            messageHelper.setText(content, true);
        };
        javaMailSender.send(messagePreparator);
        System.out.println("Sent");
    }

    @Override
    public void sendVerificationMail(String toEmailId, String verifyLink) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(fromEmailId);
            messageHelper.setTo(toEmailId);
            messageHelper.setSubject(subject);

            Map<String, Object> map = new HashMap<>();
            map.put("verifyLink", verifyLink);
            String content = mailContentBuilder.build("verificationMailTemplate", map);
            messageHelper.setText(content, true);
        };
        javaMailSender.send(messagePreparator);
        System.out.println("Sent");
    }

    @Override
    public void sendNotificationMail(String emailId, String subject, String body) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(fromEmailId);
            messageHelper.setTo(emailId);
            messageHelper.setSubject(subject);

            Map<String, Object> map = new HashMap<>();
            map.put("body", body);
            String content = mailContentBuilder.build("notificationMailTemplate", map);
            messageHelper.setText(content, true);
        };
        javaMailSender.send(messagePreparator);
        System.out.println("Sent");
    }
}
