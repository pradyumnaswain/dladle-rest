package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.entity.*;
import za.co.dladle.model.Property;
import za.co.dladle.service.PropertyAssignmentService;
import za.co.dladle.service.PropertyContactService;
import za.co.dladle.service.PropertyService;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
import za.co.dladle.serviceutil.ResponseUtil;

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
    @RequestMapping(value = ApiConstants.PROPERTY_ADD, method = RequestMethod.POST)
    public Map<String, Object> addProperty(@RequestBody(required = false) PropertyAddRequest propertyAddRequest) throws IOException

    {
        try {
            PropertyAddResponse propertyAddResponse = propertyService.addProperty(propertyAddRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, propertyAddResponse, DladleConstants.PROPERTY_ADD);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Upload Profile Pic
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_UPLOAD_IMAGE, method = RequestMethod.POST)
    public Map<String, Object> uploadProfilePicture(@RequestBody PropertyImageUploadRequest propertyImageUploadRequest) {
        try {
            String imagePath = propertyService.uploadPropertyPic(propertyImageUploadRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, imagePath, DladleConstants.PROPERTY_UPLOAD_IMAGE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Delete Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_DELETE, method = RequestMethod.POST)
    public Map<String, Object> deleteProperty(@RequestBody PropertyDeleteRequest propertyDeleteRequest) throws IOException {
        try {
            Boolean aBoolean = propertyService.deleteProperty(propertyDeleteRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, aBoolean, DladleConstants.PROPERTY_DELETE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Property of a Landlord
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_LIST, method = RequestMethod.GET)
    public Map<String, Object> listProperty() throws IOException {
        try {
            List<Property> property = propertyService.listProperties();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, property, DladleConstants.PROPERTY_LIST);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Tenants on a  Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_TENANT_LIST, method = RequestMethod.GET)
    public Map<String, Object> listTenantsOnProperty(@PathVariable long houseId) throws IOException {
        try {
            List<TenantView> tenantViews = propertyService.listTenantsOnProperty(houseId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, tenantViews, DladleConstants.PROPERTY_TENANT_LIST);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Add Contact on Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_ADD_CONTACT, method = RequestMethod.POST)
    public Map<String, Object> addContact(@RequestBody PropertyContactAddRequest propertyContactAddRequest) throws IOException {
        try {
            Boolean aBoolean = propertyContactService.addContact(propertyContactAddRequest.getPropertyContactList(), propertyContactAddRequest.getHouseId());
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, aBoolean, DladleConstants.PROPERTY_ADD_CONTACT);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Delete Contact on Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_DELETE_CONTACT, method = RequestMethod.POST)
    public Map<String, Object> deleteContact(@RequestBody DeleteContactRequest deleteContactRequest) throws IOException {
        try {
            Boolean aBoolean = propertyContactService.deleteContact(deleteContactRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, aBoolean, DladleConstants.PROPERTY_DELETE_CONTACT);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //List Contacts of a House
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_CONTACT_LIST, method = RequestMethod.GET)
    public Map<String, Object> listContactsOfProperty(@PathVariable long houseId) throws IOException {
        try {
            List<PropertyContactView> propertyContactViews = propertyContactService.listContactsOfProperty(houseId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, propertyContactViews, DladleConstants.PROPERTY_CONTACT_LIST);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Set Home
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_SET_HOME, method = RequestMethod.POST)
    public Map<String, Object> setHome(@RequestBody HomeRequest homeRequest) throws IOException {
        try {
            propertyService.setHome(homeRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.PROPERTY_SET_HOME);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Invite Tenant to Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_INVITE, method = RequestMethod.POST)
    public Map<String, Object> inviteTenantToProperty(@RequestBody PropertyInviteRequest propertyInviteRequest) throws IOException {
        try {
            propertyAssignmentService.inviteTenant(propertyInviteRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.PROPERTY_INVITE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Assign Tenant to Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_ASSIGN, method = RequestMethod.POST)
    public Map<String, Object> assignPropertyToTenant(@RequestBody PropertyAssignmentRequest propertyAssignmentRequest) throws IOException {
        try {
            propertyAssignmentService.assignPropertyToTenant(propertyAssignmentRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.PROPERTY_ASSIGN);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Reject Property Request
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.PROPERTY_DECLINE, method = RequestMethod.POST)
    public Map<String, Object> declinePropertyRequest(@RequestBody PropertyDeclineRequest propertyDeclineRequest) throws IOException {
        try {
            propertyAssignmentService.declineProperty(propertyDeclineRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.PROPERTY_DECLINE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
