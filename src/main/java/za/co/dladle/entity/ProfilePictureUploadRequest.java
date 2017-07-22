package za.co.dladle.entity;

/**
 * Created by prady on 7/23/2017.
 */
public class ProfilePictureUploadRequest {
    private String base64Image;
    private String fileName;

    public ProfilePictureUploadRequest() {
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
