package za.co.dladle.model;

import za.co.dladle.entity.RatingViewDetails;

import java.util.List;

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

    private String idNumber;

    private List<RatingViewDetails> ratingViewDetails;

    private boolean accountSet;

    private boolean accountVerified;

    private boolean paymentAccountSet;

    private boolean houseStatus;

    private Integer notificationsCount;


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

    public User(String emailId, String password, boolean verified, UserType userType, String firstName, String lastName, String idNumber, String mobileNumber, String profilePicture, boolean paymentAccountSet) {
        this.emailId = emailId;
        this.password = password;
        this.verified = verified;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.mobileNumber = mobileNumber;
        this.profilePicture = profilePicture;
        this.paymentAccountSet = paymentAccountSet;
    }

    public User(String emailId, boolean verified, UserType userType, String firstName, String lastName, String idNumber, String mobileNumber, String profilePicture, List<RatingViewDetails> ratingViewDetails) {
        this.emailId = emailId;
        this.verified = verified;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.mobileNumber = mobileNumber;
        this.profilePicture = profilePicture;
        this.ratingViewDetails = ratingViewDetails;
    }

    public User(String emailId, String firstName, String lastName, String mobileNumber, String profilePicture, String idNumber) {
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.profilePicture = profilePicture;
        this.idNumber = idNumber;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public List<RatingViewDetails> getRatingViewDetails() {
        return ratingViewDetails;
    }

    public void setRatingViewDetails(List<RatingViewDetails> ratingViewDetails) {
        this.ratingViewDetails = ratingViewDetails;
    }

    public boolean isAccountSet() {
        return accountSet;
    }

    public void setAccountSet(boolean accountSet) {
        this.accountSet = accountSet;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public boolean isPaymentAccountSet() {
        return paymentAccountSet;
    }

    public void setPaymentAccountSet(boolean paymentAccountSet) {
        this.paymentAccountSet = paymentAccountSet;
    }

    public boolean isHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(boolean houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Integer getNotificationsCount() {
        return notificationsCount;
    }

    public void setNotificationsCount(Integer notificationsCount) {
        this.notificationsCount = notificationsCount;
    }
}
