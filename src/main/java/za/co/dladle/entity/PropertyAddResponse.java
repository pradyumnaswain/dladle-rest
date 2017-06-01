package za.co.dladle.entity;

/**
 * Created by 310252258 on 19/02/2017.
 */
public class PropertyAddResponse {

    private long propertyId;

    private long houseId;

    public PropertyAddResponse() {
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

