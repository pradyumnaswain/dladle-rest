package za.co.dladle.entity;

/**
 * Created by prady on 6/14/2017.
 */
public class LeaseViewRequest {
    private Long houseId;
    private String tenantEmailId;

    public LeaseViewRequest() {
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getTenantEmailId() {
        return tenantEmailId;
    }

    public void setTenantEmailId(String tenantEmailId) {
        this.tenantEmailId = tenantEmailId;
    }
}
