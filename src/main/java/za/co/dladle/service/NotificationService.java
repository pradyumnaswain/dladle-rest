package za.co.dladle.service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by prady on 7/26/2016.
 */
@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Value("${sendgrid.api.key}")
    private String SENDGRID_API_KEY;
    @Value("${from.email}")
    private String FROM_EMAIL;
    @Value("${subject}")
    private String SUBJECT;


    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
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

    //------------------------------------------------------------------------------------------------------------------
    //Send Email
    //------------------------------------------------------------------------------------------------------------------
    public Response sendMail(String toEmailId, String verifyLink) throws IOException {
        Email from = new Email(FROM_EMAIL);
        String subject = SUBJECT;
        Email to = new Email(toEmailId);
        Content content = new Content("text/plain", "Please click on the below link to verify your EmailId.\n" + verifyLink);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
            return response;
        } catch (IOException ex) {
            return null;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Send Email
    //------------------------------------------------------------------------------------------------------------------
    public Response sendMail(String toEmailId, int otp) throws IOException {
        Email from = new Email(FROM_EMAIL);
        String subject = SUBJECT;
        Email to = new Email(toEmailId);
        Content content = new Content("text/plain", "Your OTP for password reset is : \n" + otp);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
            return response;
        } catch (IOException ex) {
            return null;
        }
    }
}
