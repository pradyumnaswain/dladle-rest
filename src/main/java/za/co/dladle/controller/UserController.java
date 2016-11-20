package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.User;
import za.co.dladle.model.UserRegisterRequest;
import za.co.dladle.service.UserService;
import za.co.dladle.util.ResponseUtil;

import javax.naming.AuthenticationException;
import java.util.Map;

/**
 * Created by prady on 11/13/2016.
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "api/user/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody User user) {
        User returnedUser;
        try {
            returnedUser = userService.login(user);
            userService.setSessionService(returnedUser);
            return ResponseUtil.response("Success", returnedUser, "Login Success");
        } catch (UserNotFoundException e) {
            return ResponseUtil.response("Fail", e.getMessage(), "Login Failed");
        }
    }

    @RequestMapping(value = "api/user/logout", method = RequestMethod.GET)
    public Map<String, Object> logout() {
        userService.logout();
        return ResponseUtil.response("Success", "{}", "Logged Out Successfully");
    }

    @RequestMapping(value = "api/user/forgot-password", method = RequestMethod.POST)
    public Map<String, Object> forgotPassword(@RequestParam String emailId) {
        try {
            User user = userService.forgotPassword(emailId);
            return ResponseUtil.response("Success", user.getEmailId(), "User Exists");
        } catch (UserNotFoundException e) {
            return ResponseUtil.response("Fail", e.getMessage(), "Failed");
        }
    }

    @RequestMapping(value = "api/user/reset-password", method = RequestMethod.POST)
    public Map<String, Object> resetPassword(@RequestBody User user) {
        userService.resetPassword(user);
        return ResponseUtil.response("Success", "{}", "Password Updated Successfully");
    }

    @RequestMapping(value = "api/user/change-password", method = RequestMethod.POST)
    public Map<String, Object> changePassword(@RequestParam String newPassword) {
        userService.changePassword(newPassword);
        return ResponseUtil.response("Success", "{}", "Password Changed Successfully");
    }

    @RequestMapping(value = "api/user/register", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody UserRegisterRequest registerRequest) {
//        userService.changePassword(newPassword);
        return ResponseUtil.response("Success", "{}", "Password Changed Successfully");
    }
}
