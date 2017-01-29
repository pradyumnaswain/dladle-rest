package za.co.dladle.service;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.dladle.entity.*;
import za.co.dladle.exception.OtpMismatchException;
import za.co.dladle.exception.UseAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.exception.UserVerificationCodeNotMatchException;
import za.co.dladle.model.User;
import za.co.dladle.session.UserSession;
import za.co.dladle.util.RandomUtil;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by prady on 11/13/2016.
 */
@Service
public class UserService {
    @Autowired
    private HttpSession session;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    UserServiceUtility userServiceUtility;

    @Autowired
    NotificationService notificationService;

    @Value("${verification.link}")
    private String verificationLink;


    //------------------------------------------------------------------------------------------------------------------
    //Set Session
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public User setSessionService(User user) {
        session.setAttribute("user", user);

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        userSession.setUser(user);

        return user;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Login
    //------------------------------------------------------------------------------------------------------------------
    public User login(UserRequest user) throws UserNotFoundException {
        String hashedPassword = Hashing.sha512().hashString(user.getPassword(), Charset.defaultCharset()).toString();

        user.setPassword(hashedPassword);

        return userServiceUtility.findUserByEmailAndPassword(user.getEmailId(), hashedPassword);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Logout
    //------------------------------------------------------------------------------------------------------------------
    public void logout() {
        session.invalidate();
    }

    //------------------------------------------------------------------------------------------------------------------
    //Forgot Password
    //------------------------------------------------------------------------------------------------------------------
    public User forgotPassword(String emailId) throws UserNotFoundException {
        return userServiceUtility.findUserByEmail(emailId);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Reset Password
    //------------------------------------------------------------------------------------------------------------------
    public void resetPassword(UserRequestForResetPassword user) throws OtpMismatchException {
        String emailId = user.getEmailId();
        String hashedPassword = Hashing.sha512().hashString(user.getNewPassword(), Charset.defaultCharset()).toString();
        userServiceUtility.updateUserPasswordWithOtp(emailId, hashedPassword,user.getOtp());
    }

    //------------------------------------------------------------------------------------------------------------------
    //Change Password
    //------------------------------------------------------------------------------------------------------------------
    public void changePassword(String newPassword) {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String emailId = userSession.getUser().getEmailId();
        String hashedPassword = Hashing.sha512().hashString(newPassword, Charset.defaultCharset()).toString();
        userServiceUtility.updateUserPassword(emailId, hashedPassword);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Register
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public void register(UserRegisterRequest user) throws UseAlreadyExistsException, IOException {

        String verification = "https://dladle-webservice.herokuapp.com/verify/";
//        String verification = "http://localhost:9098/verify/";
        String hashedCode = Hashing.sha1().hashString(user.getPassword(), Charset.defaultCharset()).toString();
        verification = verification + user.getEmailId() + "/" + hashedCode;
        int rows = userServiceUtility.userRegistration(user, hashedCode);

        if (rows == 1) {
            //send mail
            notificationService.sendMail(user.getEmailId(), verification);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Verify User
    //------------------------------------------------------------------------------------------------------------------
    public void verify(String emailId, String verificationCode) throws IOException, UserVerificationCodeNotMatchException {

        userServiceUtility.updateUserVerification(emailId, verificationCode);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Send OTP for Forgot Password
    //------------------------------------------------------------------------------------------------------------------
    public void sendOtp(String emailId) throws IOException {
        Integer otp = RandomUtil.generateRandom();
        userServiceUtility.updateUserOtp(emailId, otp);
        notificationService.sendMail(emailId, otp);
    }

    public int update(UserUpdateRequest userUpdateRequest) {
        return 0;
    }

    public int update(VendorUpdateRequest vendorUpdateRequest) {
        return 0;
    }
}
