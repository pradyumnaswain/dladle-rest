package za.co.dladle.model;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by prady on 8/3/2016.
 */
@Entity(name = "service_type")
public class ServiceType {

    @Id
    private Long id;

    @Column(unique = true, nullable = false, length = 128)
    private String type;

    //------------------------------------------------------------------------------------------------------------------
    //Constructors
    //------------------------------------------------------------------------------------------------------------------
    public ServiceType() {
    }

    public ServiceType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Getters and Setters
    //------------------------------------------------------------------------------------------------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Enums
    //------------------------------------------------------------------------------------------------------------------
    public static enum Key {
        PLUMBING(2),;

        private Long value;

        private Key(int value) {
            this.value = (long) value;
        }

        public static Key getByValue(int value) {
            return Arrays.stream(Key.values()).filter(v -> v.value == value).findFirst().get();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //get By Id
    //------------------------------------------------------------------------------------------------------------------
    public static ServiceType getById(Long id) {
        Key key = Key.getByValue(id.intValue());

        switch (key) {
            case PLUMBING:
                return PLUMBING();
            default:
                return getDefault();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Data objects
    //------------------------------------------------------------------------------------------------------------------
    public static ServiceType PLUMBING() {
        return new ServiceType(Key.PLUMBING.value, "PLUMBING");
    }

    //------------------------------------------------------------------------------------------------------------------
    // check for equality
    //------------------------------------------------------------------------------------------------------------------
    public Boolean eqPLUMBING() {
        return getId().equals(Key.PLUMBING.value);
    }

    //------------------------------------------------------------------------------------------------------------------
    // default
    //------------------------------------------------------------------------------------------------------------------
    public static ServiceType getDefault() {
        return ServiceType.PLUMBING();
    }
}
