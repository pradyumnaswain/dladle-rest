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
    private List<ServiceDocuments> serviceDocuments;
    private User user;

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
}
