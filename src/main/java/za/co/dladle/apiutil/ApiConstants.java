package za.co.dladle.apiutil;

/**
 * Created by prady on 6/18/2017.
 */
public class ApiConstants {
    public static final String WELCOME = "/welcome";
    //------------------------------------------------------------------------------------------------------------------
    //Lease API
    //------------------------------------------------------------------------------------------------------------------
    public static final String LEASE_VIEW = "/api/lease/view";
    public static final String LEASE_VIEW_HOUSE_ID = "/api/lease/view/{houseId}";
    public static final String LEASE_RENEW = "/api/lease/renew";
    public static final String LEASE_TERMINATE_REQUEST = "/api/lease/terminate/request";
    public static final String LEASE_TERMINATE_ACCEPT = "/api/lease/terminate/accept";
    public static final String LEASE_TERMINATE_REJECT = "/api/lease/terminate/reject";
    public static final String LEASE_LEAVE = "/api/lease/leave";
    public static final String LEASE_LEAVE_LANDLORD = "/api/lease/remove/tenant";
    //------------------------------------------------------------------------------------------------------------------
    //Notification API
    //------------------------------------------------------------------------------------------------------------------
    public static final String NOTIFICATION_READ = "/api/notification/read";
    public static final String NOTIFICATION_LIST = "/api/notification/list";
    public static final String NOTIFICATION_LIST_HOUSE_ID = "/api/notification/list/{houseId}";
    //------------------------------------------------------------------------------------------------------------------
    //Property API
    //------------------------------------------------------------------------------------------------------------------
    public static final String PROPERTY_ADD = "/api/property/add";
    public static final String PROPERTY_UPLOAD_IMAGE = "/api/property/upload/image";
    public static final String PROPERTY_DELETE = "/api/property/delete";
    public static final String PROPERTY_LIST = "/api/property/list";
    public static final String PROPERTY_TENANT_LIST = "/api/property/tenant/list/{houseId}";

    public static final String PROPERTY_CONTACT_LIST = "/api/property/contact/list/{houseId}";
    public static final String PROPERTY_CONTACT_LIST_TENANT = "/api/property/contact/list";
    public static final String PROPERTY_ADD_CONTACT = "/api/property/add/contact";
    public static final String PROPERTY_DELETE_CONTACT = "/api/property/delete/contact";

    public static final String PROPERTY_SET_HOME = "/api/property/home";

    public static final String PROPERTY_INVITE = "/api/property/invite";
    public static final String PROPERTY_ASSIGN = "/api/property/assign";
    public static final String PROPERTY_DECLINE = "/api/property/decline";
    //------------------------------------------------------------------------------------------------------------------
    //Tenant API
    //------------------------------------------------------------------------------------------------------------------
    public static final String TENANT_CONTACT_ADD = "/api/contact/add";
    public static final String TENANT_CONTACT_DELETE = "/api/contact/delete";
    public static final String TENANT_CONTACT_LIST = "/api/contact/list";
    public static final String TENANT_PROPERTY_REQUEST = "/api/property/request";
    public static final String TENANT_PROPERTY_DOCUMENTS_ADD = "/api/property/add/documents";
    public static final String TENANT_PROPERTY_DOCUMENTS_VIEW = "/api/property/view/documents";

    //------------------------------------------------------------------------------------------------------------------
    //Rating API
    //------------------------------------------------------------------------------------------------------------------
    public static final String RATING_VIEW = "/api/rating/view";
    public static final String RATING_VIEW_DETAILS = "/api/rating/view/details";
    public static final String RATING_POST = "/api/rating/post";
    public static final String RATING_UPDATE = "/api/rating/update";

    //------------------------------------------------------------------------------------------------------------------
    //User API
    //------------------------------------------------------------------------------------------------------------------
    public static final String USER_LOGIN = "api/user/login";
    public static final String USER_DETAILS = "api/user/get/details";
    public static final String USER_LOGOUT = "api/user/logout";
    public static final String USER_FORGOT_PASSWORD = "api/user/forgot-password";
    public static final String USER_RESET_PASSWORD = "api/user/reset-password";
    public static final String USER_CHANGE_PASSWORD = "api/user/change-password";
    public static final String USER_REGISTER = "api/user/register";
    public static final String USER_UPLOAD_PIC = "api/user/profile/upload/image";
    public static final String USER_VERIFY = "/verify/{emailId}/{hashedCode}";
    public static final String USER_TENANT_UPDATE = "/api/user/tenant/update";
    public static final String USER_LANDLORD_UPDATE = "/api/user/landlord/update";
    public static final String USER_VENDOR_UPDATE = "/api/user/vendor/update";
    public static final String USER_SEARCH = "/api/user/search";
    public static final String USER_DEVICE_ADD = "/api/user/add/deviceid";
    //------------------------------------------------------------------------------------------------------------------
    //Wallet API
    //------------------------------------------------------------------------------------------------------------------
    public static final String CARD_ADD = "/api/wallet/add/card";
    public static final String CARD_UPDATE = "/api/wallet/update/card";
    public static final String CARD_DELETE = "/api/wallet/delete/card";
    public static final String CARD_VIEW = "/api/wallet/view/card";
    //------------------------------------------------------------------------------------------------------------------
    //Vendor API
    //------------------------------------------------------------------------------------------------------------------
    public static final String VENDOR_REQUEST = "/api/vendor/post/request";
    public static final String VENDOR_VIEW_WORK = "/api/vendor/view/work/{serviceId}";
    public static final String VENDOR_ON_WORK = "/api/vendor/on/work";
    public static final String VENDOR_OFF_WORK = "/api/vendor/off/work";
    public static final String VENDOR_CURRENT_STATUS = "/api/vendor/work/status";
    public static final String VENDOR_ESTIMATE = "/api/vendor/work/estimate";
}
