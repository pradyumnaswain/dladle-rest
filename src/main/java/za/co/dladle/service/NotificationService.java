package za.co.dladle.service;

/**
 * Created by prady on 3/24/2017.
 */
public interface NotificationService {
    void sendWelcomeMail(String toEmailId);

    void sendOtpMail(String toEmailId, int otp);

    void sendVerificationMail(String toEmailId, String verifyLink);

    void sendPropertyInviteMail(String toEmailId);

    void sendPropertyRequesteMail(String toEmailId);

    void sendPropertyDeclineMail(String emailId);
}
