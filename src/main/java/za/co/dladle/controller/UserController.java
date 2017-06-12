package za.co.dladle.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.entity.*;
import za.co.dladle.exception.OtpMismatchException;
import za.co.dladle.exception.UseAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.exception.UserVerificationCodeNotMatchException;
import za.co.dladle.exception.PasswordMismatchException;
import za.co.dladle.model.User;
import za.co.dladle.model.UserType;
import za.co.dladle.service.UserService;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 11/13/2016.
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    //------------------------------------------------------------------------------------------------------------------
    //Login
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody UserRequest user) {
        User returnedUser;
        try {
            returnedUser = userService.login(user);

            if (returnedUser.isVerified()) {

//                if (user.getDeviceId() != null) {
//                    userService.saveDeviceDetails(user.getEmailId(), user.getDeviceId());
//                }
                userService.setSessionService(returnedUser);
                return ResponseUtil.response("SUCCESS", returnedUser, "Login Success");
            } else {
                return ResponseUtil.response("Not Verified", null, "Please verify your Email to continue");
            }
        } catch (UserNotFoundException e) {
            // TODO: 1/8/2017 Chnage Message to wrong username or password
            return ResponseUtil.response("FAIL", null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Logout
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/get/details", method = RequestMethod.GET)
    public Map<String, Object> getUserDetails(@RequestParam String emailId) throws UserNotFoundException {
        try {
            User user = userService.getDetails(emailId);
            return ResponseUtil.response("SUCCESS", user, "Logged Out Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Logout
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/logout", method = RequestMethod.GET)
    public Map<String, Object> logout() throws UserNotFoundException {
        userService.logout();
        return ResponseUtil.response("SUCCESS", "{}", "Logged Out Successfully");
    }

    //------------------------------------------------------------------------------------------------------------------
    //Forgot Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/forgot-password", method = RequestMethod.POST)
    public Map<String, Object> forgotPassword(@RequestBody ForgotPasswordRequest passwordRequest) {
        try {
            User user = userService.forgotPassword(passwordRequest.getEmailId());
            userService.sendOtp(user.getEmailId());
            return ResponseUtil.response("SUCCESS", user.getEmailId(), "User Exists");
        } catch (UserNotFoundException | IOException e) {
            return ResponseUtil.response("FAIL", null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Reset Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/reset-password", method = RequestMethod.POST)
    public Map<String, Object> resetPassword(@RequestBody UserRequestForResetPassword user) {
        try {
            userService.resetPassword(user);
            return ResponseUtil.response("SUCCESS", "{}", "Password Updated Successfully");
        } catch (OtpMismatchException e) {
            e.printStackTrace();
            return ResponseUtil.response("FAIL", null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Change Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/change-password", method = RequestMethod.POST)
    public Map<String, Object> changePassword(@RequestBody ChangePasswordRequest changePassword) {
        try {
            userService.changePassword(changePassword);
            return ResponseUtil.response("SUCCESS", "{}", "Password Changed Successfully");
        } catch (PasswordMismatchException e) {
            e.printStackTrace();
            return ResponseUtil.response("FAIL", null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Register
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Register API", notes = "UserType=[TENANT,VENDOR,LANDLORD]")
    @RequestMapping(value = "api/user/register", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody(required = false) UserRegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
            return ResponseUtil.response("SUCCESS", "{}", "User Registered Successfully");
        } catch (UseAlreadyExistsException e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        } catch (IOException e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Upload Profile Pic
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Upload user profile pic", notes = "")
    @RequestMapping(value = "api/user/profile/upload/image", method = RequestMethod.POST)
    public Map<String, Object> uploadProfilePicture(@RequestBody String base64Image) {
        try {
            String imagePath = userService.uploadProfilePic(base64Image);
            return ResponseUtil.response("SUCCESS", imagePath, "Profile picture uploaded Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Verify User
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/verify/{emailId}/{hashedCode}", method = RequestMethod.GET)
    public String verifyUser(@PathVariable String emailId, @PathVariable String hashedCode) throws IOException {
        try {
            userService.verify(emailId, hashedCode);
            return "Verified";
        } catch (UserVerificationCodeNotMatchException e) {
            return e.getMessage();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update User Profile
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/user/tenant/update", method = RequestMethod.POST)
    public Map<String, Object> updateTenant(@RequestBody(required = false) UserUpdateRequest userUpdateRequest) throws IOException {
        try {
            int rows = userService.update(userUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response("SUCCESS", "{}", "User Updated Successfully");
            } else {
                return ResponseUtil.response("SUCCESS", "{}", "Unable to update User");
            }
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update User Profile
    //------------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Update Landlord", notes = "HomeViewType=[HOME_VIEW_TYPE_1,HOME_VIEW_TYPE_2,HOME_VIEW_TYPE_3]")
    @RequestMapping(value = "/api/user/landlord/update", method = RequestMethod.POST)
    public Map<String, Object> updateLandlord(@RequestBody(required = false) LandlordUpdateRequest landlordUpdateRequest) throws IOException {
        try {
            int rows = userService.update(landlordUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response("SUCCESS", "{}", "LandLord Updated Successfully");
            } else {
                return ResponseUtil.response("SUCCESS", "{}", "Unable to update LandLord");
            }
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update Vendor Profile
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Update Vendor", notes = "ServiceType=[PLUMBER, ELECTRICIAN,PAINTER],experienceType=[ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,MORE_THAN_TEN] ")
    @RequestMapping(value = "/api/user/vendor/update", method = RequestMethod.POST)
    public Map<String, Object> updateVendor(@RequestBody(required = false) VendorUpdateRequest vendorUpdateRequest) throws IOException {
        try {
            int rows = userService.update(vendorUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response("SUCCESS", "{}", "Vendor updated Successfully");
            } else {
                return ResponseUtil.response("SUCCESS", "{}", "Unable to update Vendor");
            }
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Search for a User
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Search User", notes = "Search")
    @RequestMapping(value = "/api/user/search", method = RequestMethod.POST)
    public Map<String, Object> updateVendor(@RequestBody UserSearchRequest userSearchRequest) throws IOException {
        try {
            List<UserSearchResponse> userSearchResponseList = userService.search(userSearchRequest);
            return ResponseUtil.response("SUCCESS", userSearchResponseList, "User Fetched Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Search for a User
    //------------------------------------------------------------------------------------------------------------------
    @ApiOperation(value = "Add Device Id", notes = "Add Device Id")
    @RequestMapping(value = "/api/user/add/deviceid", method = RequestMethod.POST)
    public Map<String, Object> updateVendor(@RequestParam String deviceId, @RequestParam(required = false) String emailId) throws IOException {
        try {
            userService.saveDeviceDetails(emailId, deviceId);
            return ResponseUtil.response("SUCCESS", "", "Device Details Saved Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("FAIL", "{}", e.getMessage());
        }
    }
}
