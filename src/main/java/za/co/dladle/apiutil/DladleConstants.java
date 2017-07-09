package za.co.dladle.apiutil;

/**
 * Created by prady on 6/18/2017.
 */
public class DladleConstants {

    public static final String SUCCESS_RESPONSE = "SUCCESS";
    public static final String FAILURE_RESPONSE = "FAIL";
    public static final String FAILURE = "Unable to Process your Request";
    //------------------------------------------------------------------------------------------------------------------
    //Lease
    //------------------------------------------------------------------------------------------------------------------
    public static final String LEASE_TENANT = "LeaseTenant fetched Successfully";
    public static final String LEASE_LANDLORD = "LeaseLandlord fetched Successfully";
    public static final String LEASE_TERMINATE = "Lease termination requested Successfully";
    public static final String LEASE_TERMINATE_ACCEPT = "Lease termination accepted Successfully";
    public static final String LEASE_TERMINATE_REJECT = "Lease termination rejected Successfully";
    public static final String LEASE_LEAVE = "Lease left Successfully";
    //------------------------------------------------------------------------------------------------------------------
    //Notification
    //------------------------------------------------------------------------------------------------------------------
    public static final String NOTIFICATION_READ = "Notification read";
    public static final String NOTIFICATION_LISTED = "Notifications listed Successfully";
    //------------------------------------------------------------------------------------------------------------------
    //Property
    //------------------------------------------------------------------------------------------------------------------
    public static final String PROPERTY_ADD = "Property added Successfully";
    public static final String PROPERTY_UPLOAD_IMAGE = "Profile picture uploaded Successfully";
    public static final String PROPERTY_DELETE = "Property deleted Successfully";
    public static final String PROPERTY_LIST = "Property listed Successfully";
    public static final String PROPERTY_TENANT_LIST = "Tenants for Property listed Successfully";

    public static final String PROPERTY_CONTACT_LIST = "Contacts for Property listed Successfully";
    public static final String PROPERTY_ADD_CONTACT = "Property Contact Added Successfully";
    public static final String PROPERTY_DELETE_CONTACT = "Property Contact deleted Successfully";

    public static final String PROPERTY_SET_HOME = "Property Home Assigned";

    public static final String PROPERTY_INVITE = "Property Invitation Sent";
    public static final String PROPERTY_ASSIGN = "Property Assigned";
    public static final String PROPERTY_DECLINE = "Property Declined";
    //------------------------------------------------------------------------------------------------------------------
    //Tenant API
    //------------------------------------------------------------------------------------------------------------------
    public static final String TENANT_CONTACT_ADD = "Contacts Added Successfully";
    public static final String TENANT_CONTACT_DELETE = "Contacts deleted Successfully";
    public static final String TENANT_CONTACT_LIST = "Contacts listed Successfully";
    public static final String TENANT_PROPERTY_REQUEST = "Property Requested";
    public static final String TENANT_PROPERTY_DOCUMENTS_ADD = "Images and notes attached successfully";
    public static final String TENANT_PROPERTY_DOCUMENTS_VIEW = "Images and notes retrieved successfully";
    //------------------------------------------------------------------------------------------------------------------
    //Rating
    //------------------------------------------------------------------------------------------------------------------
    public static final String RATING_VIEW = "Rate retrieved Successfully";
    public static final String RATING_VIEW_DETAILS = "Rate retrieved Successfully";
    public static final String RATING_POST = "Rate posted Successfully";
    public static final String RATING_UPDATE = "Rate updated Successfully";
    //------------------------------------------------------------------------------------------------------------------
    //User API
    //------------------------------------------------------------------------------------------------------------------
    public static final String USER_LOGIN = "Login Success";
    public static final String USER_LOGIN_NOT_VERIFIED = "Please verify your Email to continue";
    public static final String USER_DETAILS = "User details fetched Successfully";
    public static final String USER_LOGOUT = "Logged Out Successfully";
    public static final String USER_FORGOT_PASSWORD = "User Exists";
    public static final String USER_RESET_PASSWORD = "Password Updated Successfully";
    public static final String USER_CHANGE_PASSWORD = "Password Changed Successfully";
    public static final String USER_REGISTER = "User Registered Successfully";
    public static final String USER_UPLOAD_PIC = "Profile picture uploaded Successfully";
    public static final String USER_VERIFY = "Verified";
    public static final String USER_UPDATE = "User Updated Successfully";
    public static final String USER_UNABLE_UPDATE = "Unable to update User";
    public static final String USER_SEARCH = "User Fetched Successfully";
    public static final String USER_DEVICE_ADD = "Device Details Saved Successfully";
    //------------------------------------------------------------------------------------------------------------------
    //Wallet API
    //------------------------------------------------------------------------------------------------------------------
    public static final String CARD_ADD = "Payment Card Added Successfully";
    public static final String CARD_UPDATE = "Payment Card Updated Successfully";
    public static final String CARD_DELETE = "Payment Card deleted Successfully";
    public static final String CARD_VIEW = "Payment Card retrieved Successfully";
    //------------------------------------------------------------------------------------------------------------------
    //Vendor API
    //------------------------------------------------------------------------------------------------------------------
    public static final String REQUEST_VENDOR = "Job Requested Successfully";
    public static final String VENDOR_VIEW_WORK = "Work request fetched successfully";
    public static final String VENDOR_ON_WORK = "Vendor Status set to At Work";
    public static final String VENDOR_OFF_WORK = "Vendor Status set to off Work";
    public static final String VENDOR_CURRENT_STATUS = "Vendor Current Status received";
    public static final String VENDOR_ESTIMATE = "Work estimated successfully";

}
