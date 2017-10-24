package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
import za.co.dladle.entity.EstimateFinalPrice;
import za.co.dladle.entity.ServiceView;
import za.co.dladle.entity.VendorResponse;
import za.co.dladle.entity.VendorServiceRequest;
import za.co.dladle.service.VendorService;
import za.co.dladle.serviceutil.ResponseUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by prady on 6/27/2017.
 */
@RestController
public class VendorRequestController {

    @Autowired
    private VendorService vendorService;

    //------------------------------------------------------------------------------------------------------------------
    //Request Vendor
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.VENDOR_REQUEST, method = RequestMethod.POST)
    public Map<String, Object> requestVendor(@RequestBody(required = false) VendorServiceRequest vendorServiceRequest) throws IOException {
        try {
            System.out.println("Entered Vendor Request");
            long serviceId = vendorService.requestVendor(vendorServiceRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, serviceId, DladleConstants.REQUEST_VENDOR);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Vendor View Work
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.VENDOR_VIEW_WORK, method = RequestMethod.GET)
    public Map<String, Object> viewServiceDetails(@PathVariable Long serviceId) throws IOException {
        try {
            ServiceView serviceView = vendorService.viewWork(serviceId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, serviceView, DladleConstants.VENDOR_VIEW_WORK);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Find Correct Vendor
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.VENDOR_FIND, method = RequestMethod.GET)
    public Map<String, Object> findCorrectVendor(@PathVariable Long serviceId) throws IOException {
        try {
            VendorResponse vendor = vendorService.findRightVendor(serviceId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, vendor, DladleConstants.VENDOR_FIND);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Final Estimated Price
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.VENDOR_FINAL_ESTIMATE, method = RequestMethod.POST)
    public Map<String, Object> estimateFinalPrice(@RequestBody EstimateFinalPrice estimateFinalPrice) throws IOException {
        // TODO: 10/15/2017 Estimate Final Service Fee after viewing work on the site
        try {
            vendorService.estimateFinalPrice(estimateFinalPrice);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.VENDOR_ESTIMATE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
    // TODO: 10/15/2017 Accept decline API based on Service Fee Range
    // TODO: 10/15/2017 Accept decline based on Final Service Fee estimations
}
