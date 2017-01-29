package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.entity.*;
import za.co.dladle.exception.OtpMismatchException;
import za.co.dladle.exception.UseAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.exception.UserVerificationCodeNotMatchException;
import za.co.dladle.model.User;
import za.co.dladle.service.UserService;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
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

                userService.setSessionService(returnedUser);
                return ResponseUtil.response("Success", returnedUser, "Login Success");
            } else {
                return ResponseUtil.response("Not Verified", null, "Please verify your Email to continue");
            }
        } catch (UserNotFoundException e) {
            // TODO: 1/8/2017 Chnage Message to wrong username or password
            return ResponseUtil.response("Fail", null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Logout
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/logout", method = RequestMethod.GET)
    public Map<String, Object> logout() {
        userService.logout();
        return ResponseUtil.response("Success", "{}", "Logged Out Successfully");
    }

    //------------------------------------------------------------------------------------------------------------------
    //Forgot Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/forgot-password", method = RequestMethod.POST)
    public Map<String, Object> forgotPassword(@RequestBody ForgotPasswordRequest passwordRequest) {
        try {
            User user = userService.forgotPassword(passwordRequest.getEmailId());
            userService.sendOtp(user.getEmailId());
            return ResponseUtil.response("Success", user.getEmailId(), "User Exists");
        } catch (UserNotFoundException | IOException e) {
            return ResponseUtil.response("Fail", null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Reset Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/reset-password", method = RequestMethod.POST)
    public Map<String, Object> resetPassword(@RequestBody UserRequestForResetPassword user) {
        try {
            userService.resetPassword(user);
            return ResponseUtil.response("Success", "{}", "Password Updated Successfully");
        } catch (OtpMismatchException e) {
            e.printStackTrace();
            return ResponseUtil.response("Fail", null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Change Password
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/change-password", method = RequestMethod.POST)
    public Map<String, Object> changePassword(@RequestParam String newPassword) {
        userService.changePassword(newPassword);
        return ResponseUtil.response("Success", "{}", "Password Changed Successfully");
    }

    //------------------------------------------------------------------------------------------------------------------
    //Register
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "api/user/register", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody(required = false) UserRegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
            return ResponseUtil.response("Success", "{}", "User Registered Successfully");
        } catch (UseAlreadyExistsException e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        } catch (IOException e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
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
    @RequestMapping(value = "/api/user/update", method = RequestMethod.POST)
    public Map<String, Object> updateUser(@RequestBody(required = false) UserUpdateRequest userUpdateRequest) throws IOException {
        try {
            int rows = userService.update(userUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response("Success", "{}", "User Registered Successfully");
            } else {
                return ResponseUtil.response("Success", "{}", "Unable to update User");
            }
        } catch (Exception e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update Vendor Profile
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/user/vendor/update", method = RequestMethod.POST)
    public Map<String, Object> updateVendor(@RequestBody(required = false) VendorUpdateRequest vendorUpdateRequest) throws IOException {
        try {
            int rows = userService.update(vendorUpdateRequest);
            if (rows == 1) {
                return ResponseUtil.response("Success", "{}", "User Registered Successfully");
            } else {
                return ResponseUtil.response("Success", "{}", "Unable to update User");
            }
        } catch (Exception e) {
            return ResponseUtil.response("Fail", "{}", e.getMessage());
        }
    }
}
