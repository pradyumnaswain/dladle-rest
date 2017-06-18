package za.co.dladle.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.entity.*;
import za.co.dladle.model.Property;
import za.co.dladle.model.PropertyContact;
import za.co.dladle.service.PropertyAssignmentService;
import za.co.dladle.service.PropertyContactService;
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

    @Autowired
    private PropertyContactService propertyContactService;

    @Autowired
    private PropertyAssignmentService propertyAssignmentService;

    //------------------------------------------------------------------------------------------------------------------
    //Add Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/add", method = RequestMethod.POST)
    public Map<String, Object> addProperty(@RequestBody(required = false) PropertyAddRequest propertyAddRequest) throws IOException {
        try {
            PropertyAddResponse propertyAddResponse = propertyService.addProperty(propertyAddRequest);
            return ResponseUtil.response("SUCCESS", propertyAddResponse, "Property Added Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Upload Profile Pic
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Upload Property pic", notes = "")
    @RequestMapping(value = "api/property/upload/image", method = RequestMethod.POST)
    public Map<String, Object> uploadProfilePicture(@RequestBody PropertyImageUploadRequest propertyImageUploadRequest) {
        try {
            String imagePath = propertyService.uploadPropertyPic(propertyImageUploadRequest);
            return ResponseUtil.response("SUCCESS", imagePath, "Profile picture uploaded Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Add Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/delete", method = RequestMethod.POST)
    public Map<String, Object> deleteProperty(@RequestBody PropertyDeleteRequest propertyDeleteRequest) throws IOException {
        try {
            Boolean aBoolean = propertyService.deleteProperty(propertyDeleteRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Property deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Add Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/add/contact", method = RequestMethod.POST)
    public Map<String, Object> addContact(@RequestBody(required = false) List<PropertyContact> propertyContactList, @RequestParam Long houseId) throws IOException {
        try {
            Boolean aBoolean = propertyContactService.addContact(propertyContactList, houseId);
            return ResponseUtil.response("SUCCESS", "{}", "Property Contact Added Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Add Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/delete/contact", method = RequestMethod.POST)
    public Map<String, Object> deleteContact(@RequestBody DeleteContactRequest deleteContactRequest) throws IOException {
        try {
            Boolean aBoolean = propertyContactService.deleteContact(deleteContactRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Property Contact deleted Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Property of a Landlord
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/list", method = RequestMethod.GET)
    public Map<String, Object> listProperty() throws IOException {
        try {
            List<Property> property = propertyService.listProperties();
            return ResponseUtil.response("SUCCESS", property, "Property listed Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Property of a Landlord
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/tenant/list/{houseId}", method = RequestMethod.GET)
    public Map<String, Object> listTenantsOnProperty(@PathVariable long houseId) throws IOException {
        try {
            List<TenantView> tenantViews = propertyService.listTenantsOnProperty(houseId);
            return ResponseUtil.response("SUCCESS", tenantViews, "Tenants for Property listed Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Contacts of a House
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/contact/list/{houseId}", method = RequestMethod.GET)
    public Map<String, Object> listContactsOfProperty(@PathVariable long houseId) throws IOException {
        try {
            List<PropertyContactView> propertyContactViews = propertyContactService.listContactsOfProperty(houseId);
            return ResponseUtil.response("SUCCESS", propertyContactViews, "Contacts for Property listed Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Set Home
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/home", method = RequestMethod.POST)
    public Map<String, Object> setHome(@RequestBody HomeRequest homeRequest) throws IOException {
        try {
            propertyService.setHome(homeRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Property Home Assigned");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Assign Tenant to Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/assign", method = RequestMethod.POST)
    public Map<String, Object> assignPropertyToTenant(@RequestBody PropertyAssignmentRequest propertyAssignmentRequest) throws IOException {
        try {
            propertyAssignmentService.assignPropertyToTenant(propertyAssignmentRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Property Assigned");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Reject Property Request
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/assign", method = RequestMethod.POST)
    public Map<String, Object> declinePropertyRequest(@RequestBody PropertyDeclineRequest propertyDeclineRequest) throws IOException {
        try {
            propertyAssignmentService.declineProperty(propertyDeclineRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Property Declined");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Invite Tenant to Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/property/invite", method = RequestMethod.POST)
    public Map<String, Object> inviteTenantToProperty(@RequestBody PropertyInviteRequest propertyInviteRequest) throws IOException {
        try {
            propertyAssignmentService.inviteTenant(propertyInviteRequest);
            return ResponseUtil.response("SUCCESS", "{}", "Property Invitation Sent");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }
}
