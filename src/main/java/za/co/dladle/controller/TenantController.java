package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
import za.co.dladle.entity.*;
import za.co.dladle.service.ContactService;
import za.co.dladle.service.PropertyAssignmentService;
import za.co.dladle.service.PropertyService;
import za.co.dladle.serviceutil.ResponseUtil;

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
    private PropertyAssignmentService propertyAssignmentService;

    @Autowired
    private PropertyService propertyService;

    //------------------------------------------------------------------------------------------------------------------
    //Add Contact
    //------------------------------------------------------------------------------------------------------------------
//    @RequestMapping(value = ApiConstants.TENANT_CONTACT_ADD, method = RequestMethod.POST)
//    public Map<String, Object> addContact(@RequestBody(required = false) ContactAddRequest contactAddRequest) throws IOException {
//        try {
//            Boolean aBoolean = contactService.addContact(contactAddRequest);
//            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.TENANT_CONTACT_ADD);
//        } catch (Exception e) {
//            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
//        }
//    }

    //------------------------------------------------------------------------------------------------------------------
    //Delete Contact
    //------------------------------------------------------------------------------------------------------------------
//    @RequestMapping(value = ApiConstants.TENANT_CONTACT_DELETE, method = RequestMethod.POST)
//    public Map<String, Object> deleteContact(@RequestBody DeleteContactRequest deleteContactRequest) throws IOException {
//        try {
//            Boolean aBoolean = contactService.deleteContact(deleteContactRequest);
//            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.TENANT_CONTACT_DELETE);
//        } catch (Exception e) {
//            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
//        }
//    }

    //------------------------------------------------------------------------------------------------------------------
    //List Property of a Landlord
    //------------------------------------------------------------------------------------------------------------------
//    @RequestMapping(value = ApiConstants.TENANT_CONTACT_LIST, method = RequestMethod.GET)
//    public Map<String, Object> listContact() throws IOException {
//        try {
//            List<TenantContactView> contactList = contactService.listContacts();
//            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, contactList, DladleConstants.TENANT_CONTACT_LIST);
//        } catch (Exception e) {
//            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
//        }
//    }
//
    //------------------------------------------------------------------------------------------------------------------
    //Invite Tenant to Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.TENANT_PROPERTY_REQUEST, method = RequestMethod.POST)
    public Map<String, Object> inviteTenantToProperty(@RequestBody PropertyRequest propertyRequest) throws IOException {
        try {
            propertyAssignmentService.requestLandlord(propertyRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.TENANT_PROPERTY_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Add Images of a property Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.TENANT_PROPERTY_DOCUMENTS_ADD, method = RequestMethod.POST)
    public Map<String, Object> addDocumentsOfProperty(@RequestBody PropertyAddDocumentsRequest propertyAddDocumentsRequest) throws IOException {
        try {
            propertyService.addDocuments(propertyAddDocumentsRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.TENANT_PROPERTY_DOCUMENTS_ADD);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Add Images of a property Property
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.TENANT_PROPERTY_DOCUMENTS_VIEW, method = RequestMethod.GET)
    public Map<String, Object> addDocumentsOfProperty() throws IOException {
        try {
            List<PropertyViewDocuments> viewDocuments = propertyService.viewDocuments();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, viewDocuments, DladleConstants.TENANT_PROPERTY_DOCUMENTS_VIEW);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
