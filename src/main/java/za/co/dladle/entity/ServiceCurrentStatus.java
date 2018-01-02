package za.co.dladle.entity;

public class ServiceCurrentStatus {
    private int numberOfVendorsNotified;
    private int numberOfVendorsQuoted;
    private String currentStatus;

    public ServiceCurrentStatus(int numberOfVendorsNotified, int numberOfVendorsQuoted, String currentStatus) {
        this.numberOfVendorsNotified = numberOfVendorsNotified;
        this.numberOfVendorsQuoted = numberOfVendorsQuoted;
        this.currentStatus = currentStatus;
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
}
