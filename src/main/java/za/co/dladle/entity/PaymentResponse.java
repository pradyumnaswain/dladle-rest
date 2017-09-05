package za.co.dladle.entity;

import java.util.List;

/**
 * Created by prady on 9/5/2017.
 */
public class PaymentResponse {
    private String statusName;

    private String statusDetails;

    private String redirectUrl;

    private List<UrlParams> urlParams;

    public PaymentResponse() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public List<UrlParams> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(List<UrlParams> urlParams) {
        this.urlParams = urlParams;
    }
}
