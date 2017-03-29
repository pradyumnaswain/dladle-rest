package za.co.dladle.entity;

import za.co.dladle.model.HomeViewType;

/**
 * Created by prady on 11/20/2016.
 */
public class LandlordUpdateRequest {

    private String firstName;

    private String lastName;

    private String identityNumber;

    private String cellNumber;

    private HomeViewType homeViewType;

    public LandlordUpdateRequest() {
    }

    public HomeViewType getHomeViewType() {
        return homeViewType;
    }

    public void setHomeViewType(HomeViewType homeViewType) {
        this.homeViewType = homeViewType;
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

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }
}
