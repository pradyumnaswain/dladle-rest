package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.entity.PropertyAddRequest;
import za.co.dladle.entity.propertyUpdateRequest;
import za.co.dladle.exception.PropertyAlreadyExistsException;
import za.co.dladle.service.PropertyServiceUtility;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Jugal on 29/01/2017.
 */
@RestController
public class PropertyController {

    @Autowired
    PropertyServiceUtility propertyServiceUtility;
    //------------------------------------------------------------------------------------------------------------------
    //Add Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/add", method = RequestMethod.POST)
    public Map<String, Object> AddProperty(@RequestBody(required = false) PropertyAddRequest propertyAddRequest) throws IOException {
        try {
            propertyServiceUtility.propertyRegistration(propertyAddRequest);
            return ResponseUtil.response("Success", "{}", "Property Registered Successfully");
        } catch (PropertyAlreadyExistsException e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        } catch (Exception e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/update", method = RequestMethod.POST)
    public Map<String, Object> updateProperty(@RequestBody(required = false) propertyUpdateRequest propertyUpdateRequest) throws IOException {
        try {
            int rows = propertyServiceUtility.updateProperty(propertyUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response("Success", "{}", "Property updated Successfully");
            } else {
                return ResponseUtil.response("Fail", "{}", "Unable to update Property");
            }
        } catch (Exception e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        }
    }
}
