package za.co.dladle.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.entity.PropertyAddRequest;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Jugal on 29/01/2017.
 */
@RestController
public class PropertyController {
    //------------------------------------------------------------------------------------------------------------------
    //Add Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/add", method = RequestMethod.POST)
    public Map<String, Object> AddProperty(@RequestBody(required = false) PropertyAddRequest propertyAddRequest) throws IOException {
        return null;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/update", method = RequestMethod.POST)
    public Map<String, Object> updateProperty(@RequestBody(required = false) PropertyAddRequest propertyAddRequest) throws IOException {
        return null;
    }
}
