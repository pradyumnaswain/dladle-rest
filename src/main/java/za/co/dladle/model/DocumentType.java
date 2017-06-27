package za.co.dladle.model;

/**
 * Created by prady on 7/26/2016.
 */
public enum DocumentType {
    AUDIO("Audio"),
    IMAGE("Image");

    private final String id;

    private DocumentType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
