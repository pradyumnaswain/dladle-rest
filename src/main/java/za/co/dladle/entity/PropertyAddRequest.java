package za.co.dladle.entity;

import za.co.dladle.model.PlaceType;

/**
 * Created by Jugal on 29/01/2017.
 */
public class PropertyAddRequest {

    private String address;

    private boolean isInEstate;

    private String estateName;

    private PlaceType placeType;

    private String complexName;

    private String unitNo;

    private String placeImage;

    public PropertyAddRequest() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isInEstate() {
        return isInEstate;
    }

    public void setInEstate(boolean inEstate) {
        isInEstate = inEstate;
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

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(String placeImage) {
        this.placeImage = placeImage;
    }
}
