package za.co.dladle.entity;

import za.co.dladle.model.User;

import java.util.List;

/**
 * Created by prady on 6/29/2017.
 */
public class ServiceView {
    private boolean emergency;
    private String serviceNeedTime;
    private String serviceDescription;
    private String propertyAddress;
    private List<ServiceDocuments> serviceDocuments;
    private User user;
    private ServiceEstimateView serviceEstimateView;

    public ServiceView() {
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public String getServiceNeedTime() {
        return serviceNeedTime;
    }

    public void setServiceNeedTime(String serviceNeedTime) {
        this.serviceNeedTime = serviceNeedTime;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public List<ServiceDocuments> getServiceDocuments() {
        return serviceDocuments;
    }

    public void setServiceDocuments(List<ServiceDocuments> serviceDocuments) {
        this.serviceDocuments = serviceDocuments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceEstimateView getServiceEstimateView() {
        return serviceEstimateView;
    }

    public void setServiceEstimateView(ServiceEstimateView serviceEstimateView) {
        this.serviceEstimateView = serviceEstimateView;
    }
}
