package za.co.dladle.entity;

/**
 * Created by prady on 10/15/2017.
 */
public class EstimateFinalPrice {
    private Long serviceId;
    private Double finalPrice;

    public EstimateFinalPrice() {
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
