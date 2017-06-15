package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.entity.*;
import za.co.dladle.service.ContactService;
import za.co.dladle.service.PropertyService;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/4/2017.
 */
@RestController
public class TenantController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private PropertyService propertyService;

    //------------------------------------------------------------------------------------------------------------------
    //Add Contact
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/contact/add", method = RequestMethod.POST)
    public Map<String, Object> addContact(@RequestBody(required = false) ContactAddRequest contactAddRequest) throws IOException {
        try {
            Boolean aBoolean = contactService.addContact(contactAddRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Contacts Added Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Delete Contact
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/contact/delete", method = RequestMethod.POST)
    public Map<String, Object> deleteContact(@RequestBody DeleteContactRequest deleteContactRequest) throws IOException {
        try {
            Boolean aBoolean = contactService.deleteContact(deleteContactRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Contacts deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Property of a Landlord
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/contact/list", method = RequestMethod.GET)
    public Map<String, Object> listContact() throws IOException {
        try {
            List<TenantContactView> contactList = contactService.listContacts();
            return ResponseUtil.response("SUCCESS", contactList, "Contacts listed Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Invite Tenant to Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/request", method = RequestMethod.POST)
    public Map<String, Object> inviteTenantToProperty(@RequestBody PropertyRequest propertyRequest) throws IOException {
        try {
            propertyService.requestLandlord(propertyRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Property Requested");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

}
