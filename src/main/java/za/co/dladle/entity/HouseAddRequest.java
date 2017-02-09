package za.co.dladle.entity;

/**
 * Created by Jugal on 29/01/2017.
 */
public class HouseAddRequest {

    private int houseNumber;

    private int propertyId;

    public HouseAddRequest() {
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
}
