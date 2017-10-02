package za.co.dladle.jpamodel;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by prady on 10/2/2017.
 */
@Entity
@Table(name = "user_dladle")
public class UserDladle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "cell_number")
    private String cellNumber;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "emailid")
    private String emailId;

    @Column(name = "password")
    private String password;

    @ManyToOne
    private UserTypeDladle userType;

    @Column(name = "registered_date")
    private LocalDateTime registeredDate;

    @Column(name = "last_logged_in_date")
    private LocalDateTime lastLoggedInDate;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "verified")
    private boolean verified = false;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "payment_account_Set")
    private boolean paymentAccountSet = false;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "otp")
    private Long otp;

    public UserDladle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
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

    public UserTypeDladle getUserType() {
        return userType;
    }

    public void setUserType(UserTypeDladle userType) {
        this.userType = userType;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public LocalDateTime getLastLoggedInDate() {
        return lastLoggedInDate;
    }

    public void setLastLoggedInDate(LocalDateTime lastLoggedInDate) {
        this.lastLoggedInDate = lastLoggedInDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isPaymentAccountSet() {
        return paymentAccountSet;
    }

    public void setPaymentAccountSet(boolean paymentAccountSet) {
        this.paymentAccountSet = paymentAccountSet;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }
}
