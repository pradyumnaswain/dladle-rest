package za.co.dladle.entity;

import za.co.dladle.model.ServiceType;

/**
 * Created by prady on 11/20/2016.
 */
public class VendorUpdateRequest {

    private String firstName;

    private String lastName;

    private String identityNumber;

    private Integer cellNumber;

    private String businessName;

    private String businessAddress;

    private ServiceType serviceType;

    private boolean tools;

    private boolean transport;

    private Integer experience;

    public VendorUpdateRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Integer getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(Integer cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public boolean isTools() {
        return tools;
    }

    public void setTools(boolean tools) {
        this.tools = tools;
    }

    public boolean isTransport() {
        return transport;
    }

    public void setTransport(boolean transport) {
        this.transport = transport;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
