package za.co.dladle.entity;

/**
 * Created by jugal on 09/02/2017.
 */
public class propertyUpdateRequest {

    private String complexName;

    private String unitNumber;

    private String imgUrl;

    private boolean IsEstate;

    private String estateName;

    public propertyUpdateRequest() {
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
}
