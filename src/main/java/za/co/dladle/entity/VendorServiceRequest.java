package za.co.dladle.entity;

import za.co.dladle.model.ServiceType;

import java.util.List;

/**
 * Created by prady on 6/27/2017.
 */
public class VendorServiceRequest {
    private boolean isEmergency;
    private String serviceNeedTime;
    private ServiceType serviceType;
    private List<ServiceDocuments> serviceDocuments;
    private String serviceNote;
    private long houseId;

    public VendorServiceRequest() {
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }

    public String getServiceNeedTime() {
        return serviceNeedTime;
    }

    public void setServiceNeedTime(String serviceNeedTime) {
        this.serviceNeedTime = serviceNeedTime;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public List<ServiceDocuments> getServiceDocuments() {
        return serviceDocuments;
    }

    public void setServiceDocuments(List<ServiceDocuments> serviceDocuments) {
        this.serviceDocuments = serviceDocuments;
    }

    public String getServiceNote() {
        return serviceNote;
    }

    public void setServiceNote(String serviceNote) {
        this.serviceNote = serviceNote;
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }
}
