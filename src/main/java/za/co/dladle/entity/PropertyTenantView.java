package za.co.dladle.entity;

import za.co.dladle.model.User;

import java.util.List;

/**
 * Created by prady on 6/1/2017.
 */
public class PropertyTenantView {
    private long propertyId;
    private long houseId;
    private String address;
    private String placeType;
    private String complexName;
    private String unitNumber;
    private String placeImage;
    private boolean isEstate;
    private String estateName;
    private int notificationsCount;
    private int contactsCount;
    private int tenantsCount;
    private boolean activeJob;
    private boolean isHome;
    private List<PropertyContactView> propertyContactList;
    private User landlord;

    public PropertyTenantView() {
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

    public int getNotificationsCount() {
        return notificationsCount;
    }

    public void setNotificationsCount(int notificationsCount) {
        this.notificationsCount = notificationsCount;
    }

    public int getContactsCount() {
        return contactsCount;
    }

    public void setContactsCount(int contactsCount) {
        this.contactsCount = contactsCount;
    }

    public int getTenantsCount() {
        return tenantsCount;
    }

    public void setTenantsCount(int tenantsCount) {
        this.tenantsCount = tenantsCount;
    }

    public boolean isActiveJob() {
        return activeJob;
    }

    public void setActiveJob(boolean activeJob) {
        this.activeJob = activeJob;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public User getLandlord() {
        return landlord;
    }

    public void setLandlord(User landlord) {
        this.landlord = landlord;
    }
}
