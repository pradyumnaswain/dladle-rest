package za.co.dladle.entity;

public class ServiceCurrentStatus {
    private int numberOfVendorsNotified;
    private int numberOfVendorsQuoted;
    private String currentStatus;
    private int statusType;

    public ServiceCurrentStatus(int numberOfVendorsNotified, int numberOfVendorsQuoted, String currentStatus, int statusType) {
        this.numberOfVendorsNotified = numberOfVendorsNotified;
        this.numberOfVendorsQuoted = numberOfVendorsQuoted;
        this.currentStatus = currentStatus;
        this.statusType = statusType;
    }

    public ServiceCurrentStatus() {
    }

    public int getNumberOfVendorsNotified() {
        return numberOfVendorsNotified;
    }

    public void setNumberOfVendorsNotified(int numberOfVendorsNotified) {
        this.numberOfVendorsNotified = numberOfVendorsNotified;
    }

    public int getNumberOfVendorsQuoted() {
        return numberOfVendorsQuoted;
    }

    public void setNumberOfVendorsQuoted(int numberOfVendorsQuoted) {
        this.numberOfVendorsQuoted = numberOfVendorsQuoted;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }
}
