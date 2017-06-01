package za.co.dladle.model;

import za.co.dladle.entity.PropertyContactView;

import java.util.List;

/**
 * Created by prady on 6/1/2017.
 */
public class Property {
    private long propertyId;
    private long houseId;
    private String address;
    private String placeType;
    private String complexName;
    private String unitNumber;
    private String placeImage;
    private boolean isEstate;
    private String estateName;
    private List<PropertyContactView> propertyContactList;
    private List<String> tenantList;

    public Property() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }

    public boolean isEstate() {
        return isEstate;
    }

    public void setEstate(boolean estate) {
        isEstate = estate;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public List<PropertyContactView> getPropertyContactList() {
        return propertyContactList;
    }

    public void setPropertyContactList(List<PropertyContactView> propertyContactList) {
        this.propertyContactList = propertyContactList;
    }

    public List<String> getTenantList() {
        return tenantList;
    }

    public void setTenantList(List<String> tenantList) {
        this.tenantList = tenantList;
    }
}
