package za.co.dladle.entity;

import za.co.dladle.model.ServiceType;

import java.util.List;

/**
 * Created by prady on 6/27/2017.
 */
public class VendorServiceRequest {
    private boolean isEmergency;
    private boolean ownPay;
    private String serviceNeedTime;
    private ServiceType serviceType;
    private List<ServiceDocuments> serviceDocuments;
    private String serviceNote;

    public VendorServiceRequest() {
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }

    public boolean isOwnPay() {
        return ownPay;
    }

    public void setOwnPay(boolean ownPay) {
        this.ownPay = ownPay;
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
}
