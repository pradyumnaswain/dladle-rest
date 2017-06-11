package za.co.dladle.entity;

/**
 * Created by prady on 6/11/2017.
 */
public class PropertyImageUploadRequest {
    private String base64Image;
    private long propertyId;

    public PropertyImageUploadRequest() {
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}
