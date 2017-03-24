package za.co.dladle.service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 7/26/2016.
 */
@Service
public class NotificationServiceSendGridImpl implements NotificationService {
    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Value("${sendgrid.api.key}")
    private String SENDGRID_API_KEY;
    @Value("${from.email}")
    private String FROM_EMAIL;
    @Value("${subject}")
    private String SUBJECT;

    private Response sendMailUtil(Mail mail, SendGrid sg, Request request) throws IOException {
        request.method = Method.POST;
        request.endpoint = "mail/send";
        request.body = mail.build();
        Response response = sg.api(request);
        System.out.println(response.statusCode);
        System.out.println(response.body);
        System.out.println(response.headers);
        return response;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Send Email
    //------------------------------------------------------------------------------------------------------------------
    public void sendVerificationMail(String toEmailId, String verifyLink) {
        Email from = new Email(FROM_EMAIL);
        String subject = SUBJECT;
        Email to = new Email(toEmailId);
        Content content = new Content("text/plain", "Please click on the below link to verify your EmailId.\n" + verifyLink);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            sendMailUtil(mail, sg, request);
        } catch (IOException ex) {
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Send Email
    //------------------------------------------------------------------------------------------------------------------
    public void sendOtpMail(String toEmailId, int otp) {
        Email from = new Email(FROM_EMAIL);
        String subject = SUBJECT;
        Email to = new Email(toEmailId);
        Content content = new Content("text/plain", "Your OTP for password reset is : \n" + otp);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            sendMailUtil(mail, sg, request);
        } catch (IOException ex) {
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Send Welcome Email
    //------------------------------------------------------------------------------------------------------------------
    public void sendWelcomeMail(String toEmailId) {
        Email from = new Email(FROM_EMAIL);
        Map<String, Object> map = new HashMap<>();

        map.put("emailId", toEmailId);
        String getUserSql = "SELECT first_name FROM user_dladle WHERE emailid=:emailId";
        String name = this.parameterJdbcTemplate.queryForObject(getUserSql, map, String.class);
        String subject = "Welcome to DlaDle, Welcome Home ";
        Email to = new Email(toEmailId);
        Content content = new Content("text/plain", "WELCOME to DLADLE : " + "\n" +
                "Hi " + name + "!" + "\n" +
                "Thanks for Signing Up with DLADLE" + "\n" +
                "Dladle is a Smartphone app that allows you to manage your property." + "\n" +
                "Click here to begin your awesome journey!" + "\n" +
                "Welcome home " + "\n" +
                "DlaDle");

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            sendMailUtil(mail, sg, request);
        } catch (IOException ex) {
        }
    }
}
