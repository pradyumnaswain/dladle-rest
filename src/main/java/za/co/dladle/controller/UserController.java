package za.co.dladle.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.apiutil.ApiConstants;
import za.co.dladle.apiutil.DladleConstants;
import za.co.dladle.entity.*;
import za.co.dladle.exception.*;
import za.co.dladle.model.User;
import za.co.dladle.service.UserService;
import za.co.dladle.serviceutil.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 11/13/2016.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //------------------------------------------------------------------------------------------------------------------
    //Login
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_LOGIN, method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody UserRequest user) {
        User returnedUser;
        try {
            returnedUser = userService.login(user);

            if (returnedUser.isVerified()) {

                userService.setSessionService(returnedUser);
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, returnedUser, DladleConstants.USER_LOGIN);
            } else {
                return ResponseUtil.response("Not Verified", null, DladleConstants.USER_LOGIN_NOT_VERIFIED);
            }
        } catch (UserNotFoundException e) {
            // TODO: 1/8/2017 Chnage Message to wrong username or password
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //User Details
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_DETAILS, method = RequestMethod.GET)
    public Map<String, Object> getUserDetails(@RequestParam String emailId) throws UserNotFoundException {
        try {
            User user = userService.getDetails(emailId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, user, DladleConstants.USER_DETAILS);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Validate User Details
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_VALIDATE, method = RequestMethod.GET)
    public Map<String, Object> validateUser(@RequestParam String password) throws UserNotFoundException {
        try {
            boolean b = userService.validateUser(password);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, b, DladleConstants.USER_VALIDATED);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Delete User
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_DELETE, method = RequestMethod.POST)
    public Map<String, Object> deleteUser(@RequestBody UserDeleteRequest deleteRequest) throws UserNotFoundException {
        try {
            Boolean deleted = userService.deleteUser(deleteRequest);
            if (deleted) {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, true, DladleConstants.USER_DELETED_SUCCESSFULLY);
            } else {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, deleted, DladleConstants.USER_DELETED_UNSUCCESS);
            }
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Logout
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_LOGOUT, method = RequestMethod.GET)
    public Map<String, Object> logout() throws UserNotFoundException {
        userService.logout();
        return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_LOGOUT);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Forgot Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_FORGOT_PASSWORD, method = RequestMethod.POST)
    public Map<String, Object> forgotPassword(@RequestBody ForgotPasswordRequest passwordRequest) {
        try {
            User user = userService.forgotPassword(passwordRequest.getEmailId());
            userService.sendOtp(user.getEmailId());
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, user.getEmailId(), DladleConstants.USER_FORGOT_PASSWORD);
        } catch (UserNotFoundException | IOException e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Reset Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_RESET_PASSWORD, method = RequestMethod.POST)
    public Map<String, Object> resetPassword(@RequestBody UserRequestForResetPassword user) {
        try {
            userService.resetPassword(user);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_RESET_PASSWORD);
        } catch (OtpMismatchException e) {
            e.printStackTrace();
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Change Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_CHANGE_PASSWORD, method = RequestMethod.POST)
    public Map<String, Object> changePassword(@RequestBody ChangePasswordRequest changePassword) {
        try {
            userService.changePassword(changePassword);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_CHANGE_PASSWORD);
        } catch (PasswordMismatchException e) {
            e.printStackTrace();
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Register
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Register API", notes = "UserType=[TENANT,VENDOR,LANDLORD]")
    @RequestMapping(value = ApiConstants.USER_REGISTER, method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody(required = false) UserRegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_REGISTER);
        } catch (UseAlreadyExistsException | IOException e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Upload Profile Pic
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Upload user profile pic", notes = "")
    @RequestMapping(value = ApiConstants.USER_UPLOAD_PIC, method = RequestMethod.POST)
    public Map<String, Object> uploadProfilePicture(@RequestBody ProfilePictureUploadRequest profilePictureUploadRequest) {
        try {
            String imagePath = userService.uploadProfilePic(profilePictureUploadRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, imagePath, DladleConstants.USER_UPLOAD_PIC);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Verify User
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_VERIFY, method = RequestMethod.GET)
    public String verifyUser(@PathVariable String emailId, @PathVariable String hashedCode) throws IOException {
        try {
            userService.verify(emailId, hashedCode);
            return DladleConstants.USER_VERIFY;
        } catch (UserVerificationCodeNotMatchException e) {
            return e.getMessage();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update User Profile
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.USER_TENANT_UPDATE, method = RequestMethod.POST)
    public Map<String, Object> updateTenant(@RequestBody(required = false) UserUpdateRequest userUpdateRequest) throws IOException {
        try {
            int rows = userService.update(userUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_UPDATE);
            } else {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_UNABLE_UPDATE);
            }
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update User Profile
    //------------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Update Landlord", notes = "HomeViewType=[HOME_VIEW_TYPE_1,HOME_VIEW_TYPE_2,HOME_VIEW_TYPE_3]")
    @RequestMapping(value = ApiConstants.USER_LANDLORD_UPDATE, method = RequestMethod.POST)
    public Map<String, Object> updateLandlord(@RequestBody(required = false) LandlordUpdateRequest landlordUpdateRequest) throws IOException {
        try {
            int rows = userService.update(landlordUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_UPDATE);
            } else {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_UNABLE_UPDATE);
            }
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update Vendor Profile
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Update Vendor", notes = "ServiceType=[PLUMBER, ELECTRICIAN,PAINTER],experienceType=[ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,MORE_THAN_TEN] ")
    @RequestMapping(value = ApiConstants.USER_VENDOR_UPDATE, method = RequestMethod.POST)
    public Map<String, Object> updateVendor(@RequestBody(required = false) VendorUpdateRequest vendorUpdateRequest) throws IOException {
        try {
            int rows = userService.update(vendorUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_UPDATE);
            } else {
                return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, null, DladleConstants.USER_UNABLE_UPDATE);
            }
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Search for a User
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Search User", notes = "Search")
    @RequestMapping(value = ApiConstants.USER_SEARCH, method = RequestMethod.POST)
    public Map<String, Object> searchUser(@RequestBody UserSearchRequest userSearchRequest) throws IOException {
        try {
            List<UserSearchResponse> userSearchResponseList = userService.search(userSearchRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, userSearchResponseList, DladleConstants.USER_SEARCH);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Search for a User
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Add Device Id", notes = "Add Device Id")
    @RequestMapping(value = ApiConstants.USER_DEVICE_ADD, method = RequestMethod.POST)
    public Map<String, Object> addDevice(@RequestParam String deviceId, @RequestParam(required = false) String emailId) throws IOException {
        try {
            userService.saveDeviceDetails(emailId, deviceId);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, "", DladleConstants.USER_DEVICE_ADD);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }
}
