package za.co.dladle.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.entity.HouseAddRequest;
import za.co.dladle.entity.PropertyAddRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Jugal on 29/01/2017.
 */
@RestController
public class HouseController {
    //------------------------------------------------------------------------------------------------------------------
    //Add House
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/house/add", method = RequestMethod.POST)
    public Map<String, Object> AddHouse(@RequestBody(required = false) HouseAddRequest houseAddRequest) throws IOException {
        return null;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Add Houses
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/houses/add", method = RequestMethod.POST)
    public Map<String, Object> AddHouse(@RequestBody(required = false) List<HouseAddRequest> houseAddRequests) throws IOException {
        return null;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/house/update", method = RequestMethod.POST)
    public Map<String, Object> updateHouse(@RequestBody(required = false) PropertyAddRequest propertyAddRequest) throws IOException {
        return null;
    }
}
