package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.model.LeaseLandlord;
import za.co.dladle.model.LeaseTenant;
import za.co.dladle.service.LeaseService;
import za.co.dladle.util.ApiConstants;
import za.co.dladle.util.DladleConstants;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by prady on 6/14/2017.
 */
@RestController
public class LeaseController {
    @Autowired
    private LeaseService leaseService;

    //------------------------------------------------------------------------------------------------------------------
    //Lease view
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.LEASE_VIEW, method = RequestMethod.GET)
    public Map<String, Object> viewLease() throws IOException {
        try {
            LeaseTenant leaseTenant = leaseService.viewLease();
            if (leaseTenant != null) {
                return ResponseUtil.response(ApiConstants.SUCCESS_RESPONSE, leaseTenant, DladleConstants.LEASE_TENANT);
            } else {
                return ResponseUtil.response(ApiConstants.SUCCESS_RESPONSE, null, DladleConstants.LEASE_TENANT);
            }
        } catch (Exception e) {
            return ResponseUtil.response(ApiConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Lease view
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.LEASE_VIEW_HOUSE_ID, method = RequestMethod.GET)
    public Map<String, Object> viewLease(@PathVariable long houseId) throws IOException {
        try {
            LeaseLandlord leaseLandlord = leaseService.viewLease(houseId);
            if (leaseLandlord != null) {
                return ResponseUtil.response(ApiConstants.SUCCESS_RESPONSE, leaseLandlord, DladleConstants.LEASE_LANDLORD);
            } else {
                return ResponseUtil.response(ApiConstants.SUCCESS_RESPONSE, "{}", DladleConstants.LEASE_LANDLORD);
            }
        } catch (Exception e) {
            return ResponseUtil.response(ApiConstants.FAILURE_RESPONSE, "{}", e.getMessage());
        }
    }

    // TODO: 6/15/2017 terminate Lease or Renew Lease

}
