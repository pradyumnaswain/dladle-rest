package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
import za.co.dladle.entity.VendorOnWork;
import za.co.dladle.entity.VendorServiceRequest;
import za.co.dladle.entity.VendorWorkStatus;
import za.co.dladle.service.VendorService;
import za.co.dladle.serviceutil.ResponseUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by prady on 6/27/2017.
 */
@RestController
public class VendorController {

    @Autowired
    private VendorService vendorService;

    //------------------------------------------------------------------------------------------------------------------
    //Vendor at Work
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.VENDOR_CURRENT_STATUS, method = RequestMethod.GET)
    public Map<String, Object> currentStatus() throws IOException {
        try {
            VendorWorkStatus vendorWorkStatus = vendorService.currentStatus();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, vendorWorkStatus, DladleConstants.VENDOR_CURRENT_STATUS);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Vendor at Work
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.VENDOR_ON_WORK, method = RequestMethod.POST)
    public Map<String, Object> onWork(@RequestBody VendorOnWork vendorOnWork) throws IOException {
        try {
            vendorService.onWork(vendorOnWork);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.VENDOR_ON_WORK);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Vendor off Work
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.VENDOR_OFF_WORK, method = RequestMethod.POST)
    public Map<String, Object> offWork() throws IOException {
        try {
            vendorService.offWork();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.VENDOR_OFF_WORK);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

}
