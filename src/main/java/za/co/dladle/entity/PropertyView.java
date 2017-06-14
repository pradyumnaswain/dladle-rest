package za.co.dladle.entity;

/**
 * Created by prady on 6/14/2017.
 */
public class PropertyView {
    private String address;
    private String complexName;
    private String unitNumber;
    private String placeImage;
    private boolean isEstate;
    private String estateName;

    public PropertyView() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
