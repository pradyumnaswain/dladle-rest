package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.entity.ContactAddRequest;
import za.co.dladle.entity.TenantContactView;
import za.co.dladle.service.ContactService;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 6/4/2017.
 */
@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    //------------------------------------------------------------------------------------------------------------------
    //Add Property
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

}
