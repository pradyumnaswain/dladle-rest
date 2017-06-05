package za.co.dladle.entity;

import za.co.dladle.model.PlaceType;
import za.co.dladle.model.PropertyContact;

import java.util.List;

/**
 * Created by Jugal on 29/01/2017.
 */
public class PropertyDeleteRequest {


    private long propertyId;

    private long houseId;

    public PropertyDeleteRequest() {
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }
}
