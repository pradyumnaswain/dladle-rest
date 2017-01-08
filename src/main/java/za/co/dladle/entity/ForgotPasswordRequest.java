package za.co.dladle.entity;

/**
 * Created by prady on 11/13/2016.
 */
public class ForgotPasswordRequest {
    private String emailId;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
