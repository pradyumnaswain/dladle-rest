package za.co.dladle.model;

/**
 * Created by prady on 11/20/2016.
 */
public class UserRegisterRequest {
    private Integer user_type;

    private String first_name;

    private String last_name;

    private String emailId;

    private String password;

    private String identity_number;

    private boolean verified;

    private String address;

    private String business_type;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(Integer user_type,String first_name,String last_name,String emailId,String password,String identity_number,boolean verified,String address,String business_type) {
        this.user_type = user_type;
        this.first_name = first_name;
        this.last_name = last_name;
        this.emailId = emailId;
        this.password = password;
        this.identity_number = identity_number;
        this.verified = verified;
        this.address = address;
        this.business_type = business_type;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public String getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(String identity_number) {
        this.identity_number = identity_number;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }
}
