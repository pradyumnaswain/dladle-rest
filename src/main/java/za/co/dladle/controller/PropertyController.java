package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.entity.PropertyAddRequest;
import za.co.dladle.entity.PropertyAddResponse;
import za.co.dladle.model.Property;
import za.co.dladle.service.PropertyService;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Jugal on 29/01/2017.
 */
@RestController
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    //------------------------------------------------------------------------------------------------------------------
    //Add Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/add", method = RequestMethod.POST)
    public Map<String, Object> addProperty(@RequestBody(required = false) PropertyAddRequest propertyAddRequest) throws IOException {
        try {
            Boolean aBoolean = propertyService.addProperty(propertyAddRequest);
            return ResponseUtil.response("Success", "{}", "Property Added Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Property of a Landlord
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/list", method = RequestMethod.GET)
    public Map<String, Object> listProperty() throws IOException {
        try {
            List<Property> property = propertyService.listProperties();
            return ResponseUtil.response("Success", property, "Property listed Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Assign Tenant to Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/assign", method = RequestMethod.GET)
    public Map<String, Object> assignPropertyToTenant() throws IOException {
        try {
            List<Property> property = propertyService.listProperties();
            return ResponseUtil.response("Success", property, "Property listed Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        }
    }
}
