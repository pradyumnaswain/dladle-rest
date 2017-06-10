package za.co.dladle.model;

/**
 * Created by prady on 9/3/2016.
 */
public class User {

    private String emailId;

    private String password;

    private boolean verified;

    private UserType userType;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String profilePicture;

    public User() {
    }

    public User(String emailId) {
        this.emailId = emailId;
    }

    public User(String emailId, String password, boolean verified) {
        this.emailId = emailId;
        this.password = password;
        this.verified = verified;
    }

    public User(String emailId, String password, boolean verified, UserType userType, String firstName, String lastName, String mobileNumber, String profilePicture) {
        this.emailId = emailId;
        this.password = password;
        this.verified = verified;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.profilePicture = profilePicture;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
