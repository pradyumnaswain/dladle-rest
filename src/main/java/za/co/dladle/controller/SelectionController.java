package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.service.SelectionService;
import za.co.dladle.serviceutil.ResponseUtil;

import java.util.Map;

/**
 * Created by prady on 4/1/2017.
 */
@RestController
public class SelectionController {

    @Autowired
    private SelectionService selectionService;

    @RequestMapping(value = "api/select/contacttype", method = RequestMethod.GET)
    public Map<String, Object> getContactType() {
        Map<String, String> contactTypes = selectionService.getContactTypes();

        return ResponseUtil.response("SUCCESS", contactTypes, "Contact Types Fetched");
    }

    @RequestMapping(value = "api/select/homeviewtype", method = RequestMethod.GET)
    public Map<String, Object> getHomeViewType() {
        Map<String, String> homeViewTypes = selectionService.getHomeViewTypes();

        return ResponseUtil.response("SUCCESS", homeViewTypes, "Home Types Fetched");
    }

    @RequestMapping(value = "api/select/placetype", method = RequestMethod.GET)
    public Map<String, Object> getPlaceType() {
        Map<String, String> placeTypeValues = selectionService.getPlaceTypeValues();

        return ResponseUtil.response("SUCCESS", placeTypeValues, "Place Types Fetched");
    }

    @RequestMapping(value = "api/select/usertype", method = RequestMethod.GET)
    public Map<String, Object> getUserTypes() {
        Map<String, String> userTypes = selectionService.getUserTypes();

        return ResponseUtil.response("SUCCESS", userTypes, "User Types Fetched");
    }

    @RequestMapping(value = "api/select/vendorservicetype", method = RequestMethod.GET)
    public Map<String, Object> getVendorServiceType() {
        Map<String, String> vendorServiceTypeValues = selectionService.getVendorServiceTypeValues();

        return ResponseUtil.response("SUCCESS", vendorServiceTypeValues, "Vendor Types Fetched");
    }

    @RequestMapping(value = "api/select/yearsofexperiencetype", method = RequestMethod.GET)
    public Map<String, Object> getYearsOfExperienceType() {
        Map<String, String> yearsOfExperienceTypeValues = selectionService.getYearsOfExperienceTypeValues();

        return ResponseUtil.response("SUCCESS", yearsOfExperienceTypeValues, "Years Of Experience Types Fetched");
    }

    @RequestMapping(value = "api/select/rejectionreason", method = RequestMethod.GET)
    public Map<String, Object> getRejectionReason() {
        Map<String, String> rejectionReasonValues = selectionService.getRejectionReasonValues();

        return ResponseUtil.response("SUCCESS", rejectionReasonValues, "Rejection Reason Types Fetched");
    }
}
