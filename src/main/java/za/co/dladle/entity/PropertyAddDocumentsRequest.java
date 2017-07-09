package za.co.dladle.entity;

import java.util.List;

/**
 * Created by prady on 6/3/2017.
 */
public class PropertyAddDocumentsRequest {
    private List<PropertyAddDocument> imageList;

    public PropertyAddDocumentsRequest() {
    }

    public List<PropertyAddDocument> getImageList() {
        return imageList;
    }

    public void setImageList(List<PropertyAddDocument> imageList) {
        this.imageList = imageList;
    }
}
