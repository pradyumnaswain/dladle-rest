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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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


    @Autowired
    NamedParameterJdbcTemplate parameterJdbcTemplate;

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
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String emailId = userSession.getUser().getEmailId();
        Map<String, Object> map = new HashMap<>();

        map.put("emailId", emailId);
        map.put("firstName", userUpdateRequest.getFirstName());
        map.put("lastName", userUpdateRequest.getLastName());
        map.put("identityNumber",userUpdateRequest.getIdentityNumber());
        map.put("cellNumber",userUpdateRequest.getCellNumber());


        String sql = " UPDATE user_dladle SET first_name=:firstName, last_name=:lastName, id_number=:identityNumber, cell_number=:cellNumber WHERE emailid=:emailId";
        return this.parameterJdbcTemplate.update(sql, map);
    }

    public int update(VendorUpdateRequest vendorUpdateRequest){
    UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
    String emailId = userSession.getUser().getEmailId();
    Map<String, Object> map = new HashMap<>();

        map.put("emailId", emailId);
        map.put("firstName", vendorUpdateRequest.getFirstName());;
        map.put("lastName", vendorUpdateRequest.getLastName());
        map.put("identityNumber",vendorUpdateRequest.getIdentityNumber());
        map.put("cellNumber",vendorUpdateRequest.getCellNumber());
        map.put("businessName", vendorUpdateRequest.getBusinessName());;
        map.put("businessAdress", vendorUpdateRequest.getBusinessAddress());
        map.put("serviceType",vendorUpdateRequest.getServiceType().getId());
        map.put("tools",vendorUpdateRequest.isTools());
        map.put("transport",vendorUpdateRequest.isTransport());
        map.put("experience",vendorUpdateRequest.getExperience());

        String userSql = " UPDATE user_dladle SET first_name=:firstName, last_name=:lastName, id_number=:identityNumber, cell_number=:cellNumber WHERE emailid=:emailId";
        this.parameterJdbcTemplate.update(userSql, map);
        String vendorSql = " UPDATE vendor SET  service_type_id=:serviceType, business_name=:businessName,business_address=:businessAdress, tools=:tools, transport=:transport, experience_id=:experience WHERE user_id=(select id from user_dladle where emailid=:emailId)";
        return this.parameterJdbcTemplate.update(vendorSql, map);

    }
}
