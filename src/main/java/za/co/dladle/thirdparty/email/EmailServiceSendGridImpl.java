package za.co.dladle.thirdparty.email;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prady on 7/26/2016.
 */
@Service
public class EmailServiceSendGridImpl implements EmailService {
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

    @Override
    public void sendNotificationMail(String emailId, String subject, String body) {
        Email from = new Email(FROM_EMAIL);
        Email to = new Email(emailId);
        Content content = new Content("text/html", "<html><body>" +
                "<P>Hi " + "!</p>" +
                "<P>" + body + "</P>" +
                "<P>DlaDle</P></body></html>");
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
        Content content = new Content("text/html", "<html><body><p>WELCOME to DLADLE :</p> " +
                "<P>Hi " + name + "!</p>" +
                "<P>Thanks for Signing Up with DLADLE</P>" +
                "<P>Dladle is a Smartphone app that allows you to manage your property.</P>" +
                "<P>Click here to begin your awesome journey!</P>" +
                "<P>Welcome home </P>" +
                "<P>DlaDle</P></body></html>");

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            sendMailUtil(mail, sg, request);
        } catch (IOException ex) {
        }
    }
}
