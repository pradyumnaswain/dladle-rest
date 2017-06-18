package za.co.dladle.entity;

import za.co.dladle.model.PropertyContact;

import java.util.List;

/**
 * Created by prady on 6/18/2017.
 */
public class PropertyContactAddRequest {
    private List<PropertyContact> propertyContactList;
    private Long houseId;

    public PropertyContactAddRequest() {
    }

    public List<PropertyContact> getPropertyContactList() {
        return propertyContactList;
    }

    public void setPropertyContactList(List<PropertyContact> propertyContactList) {
        this.propertyContactList = propertyContactList;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
}
