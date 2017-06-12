package za.co.dladle.entity;

/**
 * Created by prady on 6/8/2017.
 */
public class TenantView {
    private String emailId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String cellNumber;
    private String profilePicture;

    public TenantView() {
    }

    public TenantView(String emailId, String firstName, String lastName, String idNumber, String cellNumber, String profilePicture) {
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.cellNumber = cellNumber;
        this.profilePicture = profilePicture;
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
}
