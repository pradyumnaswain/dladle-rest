package za.co.dladle.entity;

import java.util.List;

/**
 * Created by prady on 6/3/2017.
 */
public class PropertyAddImagesRequest {
    private List<PropertyAddImage> imageList;

    public PropertyAddImagesRequest() {
    }

    public List<PropertyAddImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<PropertyAddImage> imageList) {
        this.imageList = imageList;
    }
}
