package za.co.dladle.entity;

/**
 * Created by prady on 7/1/2017.
 */
public class VendorResponse {
    private long vendorId;
    private String vendorName;
    private String vendorEmailId;
    private String vendorProfileImage;
    private String jobType;
    private String yearsOfExperience;
    private Double vendorRating;
    private Integer numberOfJobs;
    private Double proposedFeeStartRange;
    private Double proposedFeeEndRange;

    public VendorResponse(long vendorId, String vendorEmailId, String vendorName, String vendorProfileImage, String jobType, String yearsOfExperience) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorEmailId = vendorEmailId;
        this.vendorProfileImage = vendorProfileImage;
        this.jobType = jobType;
        this.yearsOfExperience = yearsOfExperience;
    }

    public VendorResponse() {
    }

    public String getVendorEmailId() {
        return vendorEmailId;
    }

    public void setVendorEmailId(String vendorEmailId) {
        this.vendorEmailId = vendorEmailId;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorProfileImage() {
        return vendorProfileImage;
    }

    public void setVendorProfileImage(String vendorProfileImage) {
        this.vendorProfileImage = vendorProfileImage;
    }

    public Double getVendorRating() {
        return vendorRating;
    }

    public void setVendorRating(Double vendorRating) {
        this.vendorRating = vendorRating;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Integer getNumberOfJobs() {
        return numberOfJobs;
    }

    public void setNumberOfJobs(Integer numberOfJobs) {
        this.numberOfJobs = numberOfJobs;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Double getProposedFeeStartRange() {
        return proposedFeeStartRange;
    }

    public void setProposedFeeStartRange(Double proposedFeeStartRange) {
        this.proposedFeeStartRange = proposedFeeStartRange;
    }

    public Double getProposedFeeEndRange() {
        return proposedFeeEndRange;
    }

    public void setProposedFeeEndRange(Double proposedFeeEndRange) {
        this.proposedFeeEndRange = proposedFeeEndRange;
    }

    @Override
    public String toString() {
        return "VendorResponse{" +
                "vendorId=" + vendorId +
                ", vendorName='" + vendorName + '\'' +
                ", vendorEmailId='" + vendorEmailId + '\'' +
                ", vendorProfileImage='" + vendorProfileImage + '\'' +
                ", jobType='" + jobType + '\'' +
                ", yearsOfExperience='" + yearsOfExperience + '\'' +
                ", vendorRating=" + vendorRating +
                ", numberOfJobs=" + numberOfJobs +
                ", proposedFeeStartRange=" + proposedFeeStartRange +
                ", proposedFeeEndRange=" + proposedFeeEndRange +
                '}';
    }
}
