package za.co.dladle.entity;

/**
 * Created by prady on 11/20/2016.
 */
public class UserUpdateRequest {

    private String firstName;

    private String lastName;

    private String identityNumber;

    private Integer cellNumber;


    public UserUpdateRequest() {
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

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Integer getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(Integer cellNumber) { this.cellNumber = cellNumber; }
}
