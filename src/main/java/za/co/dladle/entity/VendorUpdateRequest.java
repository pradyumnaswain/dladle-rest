package za.co.dladle.entity;

import za.co.dladle.model.ServiceType;
import za.co.dladle.model.YearsExperience;

/**
 * Created by prady on 11/20/2016.
 */
public class VendorUpdateRequest {

    private String cellNumber;

    private String businessAddress;

    private ServiceType serviceType;

    private boolean tools;

    private boolean transport;

    private YearsExperience experienceType;

    public VendorUpdateRequest() {
    }


    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
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

    public YearsExperience getExperienceType() {
        return experienceType;
    }

    public void setExperienceType(YearsExperience experienceType) {
        this.experienceType = experienceType;
    }
}
