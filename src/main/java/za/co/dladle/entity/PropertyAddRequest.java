package za.co.dladle.entity;

import za.co.dladle.model.PlaceType;

/**
 * Created by Jugal on 29/01/2017.
 */
public class PropertyAddRequest {

    private String address;

    private boolean IsEstate;

    private String estateName;

    private PlaceType placeType;

    private String complexName;

    private String unitNumber;

    private String imgUrl;

    public PropertyAddRequest( ) {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEstate() {
        return IsEstate;
    }

    public void setEstate(boolean estate) {
        IsEstate = estate;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
