package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.model.LeaseLandlord;
import za.co.dladle.model.LeaseTenant;
import za.co.dladle.service.LeaseService;
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
    @RequestMapping(value = "/api/lease/view", method = RequestMethod.GET)
    public Map<String, Object> viewLease() throws IOException {
        try {
            LeaseTenant leaseTenant = leaseService.viewLease();
            if (leaseTenant != null) {
                return ResponseUtil.response("SUCCESS", leaseTenant, "LeaseTenant fetched Successfully");
            } else {
                return ResponseUtil.response("SUCCESS", "{}", "LeaseTenant fetched Successfully");
            }
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Lease view
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/lease/view/{houseId}", method = RequestMethod.GET)
    public Map<String, Object> viewLease(@PathVariable long houseId) throws IOException {
        try {
            LeaseLandlord leaseLandlord = leaseService.viewLease(houseId);
            if (leaseLandlord != null) {
                return ResponseUtil.response("SUCCESS", leaseLandlord, "LeaseLandlord fetched Successfully");
            } else {
                return ResponseUtil.response("SUCCESS", "{}", "LeaseLandlord fetched Successfully");
            }
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

}
