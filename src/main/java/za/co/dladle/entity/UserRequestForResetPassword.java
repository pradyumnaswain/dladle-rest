package za.co.dladle.entity;

/**
 * Created by prady on 9/3/2016.
 */
public class UserRequestForResetPassword {

    private String emailId;

    private String newPassword;

    private int otp;

    public UserRequestForResetPassword() {
    }

    public UserRequestForResetPassword(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
