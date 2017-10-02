package za.co.dladle.jpamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;

/**
 * Created by prady on 10/2/2017.
 */
@Entity
@Table(name = "user_type")
public class UserTypeDladle {
    @Id
    private Long id;

    @Column(length = 128, unique = true, nullable = false)
    private String name;

    public UserTypeDladle() {
    }

    public UserTypeDladle(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    //-------------------------------------------------------------------------------------------------
    // key enum
    //-------------------------------------------------------------------------------------------------

    public static enum Key {
        TENANT(1),
        LANDLORD(2),
        VENDOR(3);

        private Long value;

        private Key(int value) {
            this.value = (long) value;
        }

        public static Key getByValue(int value) {
            return Arrays.stream(Key.values()).filter(v -> v.value == value).findFirst().get();
        }
    }

    //-------------------------------------------------------------------------------------------------
    // getById
    //-------------------------------------------------------------------------------------------------
    public static UserTypeDladle getById(Long id) {

        Key key = Key.getByValue(id.intValue());

        switch (key) {
            case TENANT:
                return tenant();
            case LANDLORD:
                return landlord();
            case VENDOR:
                return vendor();
            default:
                return getDefault();
        }
    }

    //-------------------------------------------------------------------------------------------------
    // data objects
    //-------------------------------------------------------------------------------------------------
    public static UserTypeDladle tenant() {
        return new UserTypeDladle(Key.TENANT.value, "Tenant");
    }

    public static UserTypeDladle landlord() {
        return new UserTypeDladle(Key.LANDLORD.value, "Landlord");
    }

    public static UserTypeDladle vendor() {
        return new UserTypeDladle(Key.VENDOR.value, "Vendor");
    }

    //-------------------------------------------------------------------------------------------------
    // check for equality
    //-------------------------------------------------------------------------------------------------
    public Boolean eqTenant() {
        return getId().equals(Key.TENANT.value);
    }

    public Boolean eqLandlord() {
        return getId().equals(Key.LANDLORD.value);
    }

    public Boolean eqVendor() {
        return getId().equals(Key.VENDOR.value);
    }


    //-------------------------------------------------------------------------------------------------
    // default
    //-------------------------------------------------------------------------------------------------
    public static UserTypeDladle getDefault() {
        return UserTypeDladle.tenant();
    }
}
