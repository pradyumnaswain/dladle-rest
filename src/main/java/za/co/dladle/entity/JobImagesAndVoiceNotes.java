package za.co.dladle.entity;

import java.util.List;

/**
 * Created by prady on 6/27/2017.
 */
public class JobImagesAndVoiceNotes {
    private List<String> base64;

    public JobImagesAndVoiceNotes() {
    }

    public JobImagesAndVoiceNotes(List<String> base64) {
        this.base64 = base64;
    }

    public List<String> getBase64() {
        return base64;
    }

    public void setBase64(List<String> base64) {
        this.base64 = base64;
    }
}
