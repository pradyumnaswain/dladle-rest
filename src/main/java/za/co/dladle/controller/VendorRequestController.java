package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
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
    public Map<String, Object> requestVendor(@RequestBody VendorServiceRequest vendorServiceRequest) throws IOException {
        try {
            vendorService.requestVendor(vendorServiceRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.REQUEST_VENDOR);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Vendor View Work
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.VENDOR_VIEW_WORK, method = RequestMethod.GET)
    public Map<String, Object> requestVendor(@PathVariable Long serviceId) throws IOException {
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
    @RequestMapping(value = ApiConstants.VENDOR_VIEW_WORK, method = RequestMethod.GET)
    public Map<String, Object> findCorrectVendor(@PathVariable Long serviceId) throws IOException {
        try {
            VendorResponse vendor = vendorService.findRightVendor(serviceId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, vendor, DladleConstants.VENDOR_FIND);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
