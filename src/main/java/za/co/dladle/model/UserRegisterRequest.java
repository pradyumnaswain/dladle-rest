package za.co.dladle.model;

/**
 * Created by prady on 11/20/2016.
 */
public class UserRegisterRequest {

    private String address;

    private BusinessType businessType;

    private String businessName;

    private String emailId;

    private String firstName;

    private String identityNumber;

    private String lastName;

    private String password;

    private UserType userType;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String address, BusinessType businessType, String businessName, String emailId, String firstName, String identityNumber, String lastName, String password, UserType userType) {
        this.address = address;
        this.businessType = businessType;
        this.businessName = businessName;
        this.emailId = emailId;
        this.firstName = firstName;
        this.identityNumber = identityNumber;
        this.lastName = lastName;
        this.password = password;
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
