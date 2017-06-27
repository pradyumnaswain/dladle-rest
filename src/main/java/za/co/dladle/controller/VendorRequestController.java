package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
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
    @RequestMapping(value = ApiConstants.REQUEST_VENDOR, method = RequestMethod.POST)
    public Map<String, Object> requestVendor(@RequestBody VendorServiceRequest vendorServiceRequest) throws IOException {
        try {
            vendorService.requestVendor(vendorServiceRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.REQUEST_VENDOR);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
