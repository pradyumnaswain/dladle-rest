package za.co.dladle.entity;

/**
 * Created by prady on 6/1/2017.
 */
public class TenantContactView {
    private String contactType;

    private String name;

    private String address;

    private String contactNumber;

    public TenantContactView() {
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
