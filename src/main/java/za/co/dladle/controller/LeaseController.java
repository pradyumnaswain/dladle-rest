package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.entity.LeaseRemoveTenant;
import za.co.dladle.entity.LeaseTerminateRequest;
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
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, leaseTenant, DladleConstants.LEASE_TENANT);
            } else {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.FAILURE);
            }
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
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
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, leaseLandlord, DladleConstants.LEASE_LANDLORD);
            } else {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.FAILURE);
            }
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    // TODO: 6/15/2017 terminate Lease or Renew Lease
    //------------------------------------------------------------------------------------------------------------------
    //Lease Terminate Request
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.LEASE_TERMINATE_REQUEST, method = RequestMethod.POST)
    public Map<String, Object> terminate(@RequestBody LeaseTerminateRequest leaseTerminateRequest) throws IOException {
        try {
            leaseService.terminateLease(leaseTerminateRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.LEASE_TERMINATE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Lease Terminate Request
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.LEASE_TERMINATE_ACCEPT, method = RequestMethod.POST)
    public Map<String, Object> acceptLeaseTerminate(@RequestBody LeaseTerminateRequest leaseTerminateRequest) throws IOException {
        try {
            leaseService.acceptTerminateLease(leaseTerminateRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.LEASE_TERMINATE_ACCEPT);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Lease Terminate Request
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.LEASE_TERMINATE_REJECT, method = RequestMethod.POST)
    public Map<String, Object> rejectLeaseTerminate(@RequestBody LeaseTerminateRequest leaseTerminateRequest) throws IOException {
        try {
            leaseService.rejectTerminateLease(leaseTerminateRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.LEASE_TERMINATE_REJECT);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Lease Terminate Request
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.LEASE_LEAVE, method = RequestMethod.POST)
    public Map<String, Object> leaveLease() throws IOException {
        try {
            leaseService.leaveLease();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.LEASE_LEAVE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Lease Terminate Request
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.LEASE_LEAVE_LANDLORD, method = RequestMethod.POST)
    public Map<String, Object> leaveLeaseByLandlord(@RequestBody LeaseRemoveTenant leaseRemoveTenant) throws IOException {
        try {
            leaseService.removeTenantFromLease(leaseRemoveTenant.getEmailId());
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.LEASE_LEAVE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
