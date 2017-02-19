package za.co.dladle.entity;

/**
 * Created by Jugal on 29/01/2017.
 */
public class PropertyAddRequest {

    private String address;

    private String placeName;

    // TODO: 19/02/2017 Change PlaceType, BedRoomType, HomeViewType to enum
    private int placeType;

    private String complexName;

    private String unitNumber;

    private int bedRoomType;

    private String imgUrl;

    private int homeView;

    public PropertyAddRequest( ) {
    }
    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getPlaceType() {
        return placeType;
    }

    public void setPlaceType(int placeType) {
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

    public int getBedRoomType() {
        return bedRoomType;
    }

    public void setBedRoomType(int bedRoomType) {
        this.bedRoomType = bedRoomType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getHomeView() {
        return homeView;
    }

    public void setHomeView(int homeView) {
        this.homeView = homeView;
    }
}
