package za.co.dladle.util;

/**
 * Created by prady on 6/18/2017.
 */
public class ApiConstants {
    //------------------------------------------------------------------------------------------------------------------
    //Lease API
    //------------------------------------------------------------------------------------------------------------------
    public static final String LEASE_VIEW = "/api/lease/view";
    public static final String LEASE_VIEW_HOUSE_ID = "/api/lease/view/{houseId}";
    public static final String LEASE_RENEW = "/api/lease/renew";
    public static final String LEASE_TERMINATE_REQUEST = "/api/lease/terminate/request";
    public static final String LEASE_TERMINATE_ACCEPT = "/api/lease/terminate/accept";
    public static final String LEASE_TERMINATE_REJECT = "/api/lease/terminate/reject";
    //------------------------------------------------------------------------------------------------------------------
    //Notification API
    //------------------------------------------------------------------------------------------------------------------
    public static final String NOTIFICATION_READ = "/api/notification/read";
    public static final String NOTIFICATION_LIST = "/api/notification/list";
    //------------------------------------------------------------------------------------------------------------------
    //Property API
    //------------------------------------------------------------------------------------------------------------------
    public static final String PROPERTY_ADD = "/api/property/add";
    public static final String PROPERTY_UPLOAD_IMAGE = "/api/property/upload/image";
    public static final String PROPERTY_DELETE = "/api/property/delete";
    public static final String PROPERTY_LIST = "/api/property/list";
    public static final String PROPERTY_TENANT_LIST = "/api/property/tenant/list/{houseId}";

    public static final String PROPERTY_CONTACT_LIST = "/api/property/contact/list/{houseId}";
    public static final String PROPERTY_ADD_CONTACT = "/api/property/add/contact";
    public static final String PROPERTY_DELETE_CONTACT = "/api/property/delete/contact";

    public static final String PROPERTY_SET_HOME = "/api/property/home";

    public static final String PROPERTY_INVITE = "/api/property/invite";
    public static final String PROPERTY_ASSIGN = "/api/property/assign";
    public static final String PROPERTY_DECLINE = "/api/property/decline";
    //------------------------------------------------------------------------------------------------------------------
    //Rating API
    //------------------------------------------------------------------------------------------------------------------
    public static final String RATING_VIEW = "/api/rating/view";
    public static final String RATING_VIEW_DETAILS = "/api/rating/view/details";
    public static final String RATING_POST = "/api/rating/post";
    public static final String RATING_UPDATE = "/api/rating/update";


}
