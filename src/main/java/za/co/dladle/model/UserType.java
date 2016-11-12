package za.co.dladle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

/**
 * Created by prady on 7/26/2016.
 */
@Entity(name = "user_type")
public class UserType {
    @Id
    private Long id;

    @Column(unique = true, nullable = false, length = 128)
    private String name;

    //------------------------------------------------------------------------------------------------------------------
    //Constructors
    //------------------------------------------------------------------------------------------------------------------
    public UserType() {
    }

    public UserType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Key Enums
    //------------------------------------------------------------------------------------------------------------------
    public static enum Key {
        LANDLORD(1),
        TENANT(2),
        VENDOR(3);

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
    public static UserType getById(Long id) {
        Key key = Key.getByValue(id.intValue());

        switch (key) {
            case LANDLORD:
                return LANDLORD();
            case TENANT:
                return TENANT();
            case VENDOR:
                return VENDOR();
            default:
                return getDefault();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Data objects
    //------------------------------------------------------------------------------------------------------------------
    public static UserType LANDLORD() {
        return new UserType(Key.LANDLORD.value, "landlord");
    }

    public static UserType TENANT() {
        return new UserType(Key.TENANT.value, "tenant");
    }

    public static UserType VENDOR() {
        return new UserType(Key.VENDOR.value, "vendor");
    }

    //------------------------------------------------------------------------------------------------------------------
    // check for equality
    //------------------------------------------------------------------------------------------------------------------
    public Boolean eqLANDLORD() {
        return getId().equals(Key.LANDLORD.value);
    }

    public Boolean eqTENANT() {
        return getId().equals(Key.TENANT.value);
    }

    public Boolean eqVENDOR() {
        return getId().equals(Key.VENDOR.value);
    }

    //------------------------------------------------------------------------------------------------------------------
    // default
    //------------------------------------------------------------------------------------------------------------------
    public static UserType getDefault() {
        return UserType.LANDLORD();
    }
}
