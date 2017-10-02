package za.co.dladle.thirdparty.email;

/**
 * Created by prady on 3/24/2017.
 */
public interface EmailService {
    void sendWelcomeMail(String toEmailId);

    void sendOtpMail(String toEmailId, int otp);

    void sendVerificationMail(String toEmailId, String verifyLink);

    void sendNotificationMail(String emailId, String subject, String body);
}
