package za.co.dladle.service;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.dladle.entity.*;
import za.co.dladle.exception.*;
import za.co.dladle.mapper.ServiceTypeMapper;
import za.co.dladle.mapper.UserTypeMapper;
import za.co.dladle.mapper.YearsOfExperienceTypeMapper;
import za.co.dladle.model.User;
import za.co.dladle.model.UserType;
import za.co.dladle.session.UserSession;
import za.co.dladle.util.RandomUtil;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
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
    private UserServiceUtility userServiceUtility;

    @Autowired
    private NotificationServiceSendGridImpl notificationServiceSendGridImpl;

    @Autowired
    private FileManagementServiceCloudinaryImpl fileManagementService;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${verification.link}")
    private String verificationLink;


    //------------------------------------------------------------------------------------------------------------------
    //Set Session
    //------------------------------------------------------------------------------------------------------------------
    @Transactional
    public void setSessionService(User user) {
        session.setAttribute("user", user);

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        userSession.setUser(user);

    }

    //------------------------------------------------------------------------------------------------------------------
    //Login
    //------------------------------------------------------------------------------------------------------------------
    public User login(UserRequest user) throws UserNotFoundException {
        String hashedPassword = Hashing.sha512().hashString(user.getPassword(), Charset.defaultCharset()).toString();

        return userServiceUtility.findUserByEmailAndPassword(user.getEmailId(), hashedPassword);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Logout
    //------------------------------------------------------------------------------------------------------------------
    public void logout() throws UserNotFoundException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        invalidateDeviceId(userSession.getUser().getEmailId());
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
        userServiceUtility.updateUserPasswordWithOtp(emailId, hashedPassword, user.getOtp());
    }

    //------------------------------------------------------------------------------------------------------------------
    //Change Password
    //------------------------------------------------------------------------------------------------------------------
    public void changePassword(ChangePasswordRequest changePassword) throws PasswordMismatchException {
        //UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        //String emailId = userSession.getUser().getEmailId();
        Map<String, Object> map = new HashMap<>();

        map.put("emailId", changePassword.getEmailId());

        String sql = "SELECT password FROM user_dladle WHERE emailid=:emailId";
        String oldPassword = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);
        String hashedOldPassword = Hashing.sha512().hashString(changePassword.getCurrentPassword(), Charset.defaultCharset()).toString();
        if (oldPassword.equals(hashedOldPassword)) {

            if (changePassword.getNewPassword().equals(changePassword.getNewConfirmPassword())) {
                String hashedPassword = Hashing.sha512().hashString(changePassword.getNewPassword(), Charset.defaultCharset()).toString();
                userServiceUtility.updateUserPassword(changePassword.getEmailId(), hashedPassword);
            } else {
                throw new PasswordMismatchException("Password confirmation does not match ");
            }
        } else {
            throw new PasswordMismatchException("You entered the wrong current password");

        }
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
            notificationServiceSendGridImpl.sendVerificationMail(user.getEmailId(), verification);
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
        notificationServiceSendGridImpl.sendOtpMail(emailId, otp);
    }

    public int update(UserUpdateRequest userUpdateRequest) {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String emailId = userSession.getUser().getEmailId();
        Map<String, Object> map = new HashMap<>();

        map.put("emailId", emailId);
        map.put("firstName", userUpdateRequest.getFirstName());
        map.put("lastName", userUpdateRequest.getLastName());
        map.put("identityNumber", userUpdateRequest.getIdentityNumber());
        map.put("cellNumber", userUpdateRequest.getCellNumber());


        String sql = " UPDATE user_dladle SET first_name=:firstName, last_name=:lastName, id_number=:identityNumber, cell_number=:cellNumber WHERE emailid=:emailId";
        return this.parameterJdbcTemplate.update(sql, map);
    }

    @Transactional
    public int update(VendorUpdateRequest vendorUpdateRequest) {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String emailId = userSession.getUser().getEmailId();
        Map<String, Object> map = new HashMap<>();

        map.put("emailId", emailId);
        map.put("cellNumber", vendorUpdateRequest.getCellNumber());
        map.put("businessAddress", vendorUpdateRequest.getBusinessAddress());
        map.put("serviceType", ServiceTypeMapper.getServiceType(vendorUpdateRequest.getServiceType()));
        map.put("tools", vendorUpdateRequest.isTools());
        map.put("transport", vendorUpdateRequest.isTransport());
        map.put("experience", YearsOfExperienceTypeMapper.getYearsExperience(vendorUpdateRequest.getExperienceType()));

        String userSql = " UPDATE user_dladle SET cell_number=:cellNumber WHERE emailid=:emailId";
        this.parameterJdbcTemplate.update(userSql, map);
        String vendorSql = " UPDATE vendor SET  service_type_id=:serviceType, business_address=:businessAddress, tools=:tools, transport=:transport, experience_id=:experience WHERE user_id=(SELECT id FROM user_dladle WHERE emailid=:emailId)";
        return this.parameterJdbcTemplate.update(vendorSql, map);

    }

    @Transactional
    public int update(LandlordUpdateRequest landlordUpdateRequest) {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String emailId = userSession.getUser().getEmailId();
        Map<String, Object> map = new HashMap<>();

        map.put("emailId", emailId);
        map.put("firstName", landlordUpdateRequest.getFirstName());
        map.put("lastName", landlordUpdateRequest.getLastName());
        map.put("identityNumber", landlordUpdateRequest.getIdentityNumber());
        map.put("cellNumber", landlordUpdateRequest.getCellNumber());

        String sql = " UPDATE user_dladle SET first_name=:firstName, last_name=:lastName, id_number=:identityNumber, cell_number=:cellNumber WHERE emailid=:emailId";
        return this.parameterJdbcTemplate.update(sql, map);
    }

    public List<UserSearchResponse> search(UserSearchRequest userSearchRequest) throws UserNotFoundException {
        try {
            String sql = "SELECT * FROM user_dladle INNER JOIN user_type ON user_dladle.user_type_id = user_type.id WHERE user_type_id=? AND (lower(first_name) LIKE ? OR lower(last_name)=?)";
            return this.jdbcTemplate.query(sql, new Object[]{UserTypeMapper.getUserType(userSearchRequest.getUserType()), "%" + userSearchRequest.getName().toLowerCase() + "%", "%" + userSearchRequest.getName().toLowerCase() + "%"}, (rs, rowNum) ->
                    new UserSearchResponse(rs.getString("emailId"), rs.getString("first_name") + " " + rs.getString("last_name")));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User Not Found");
        }

    }

    public void saveDeviceDetails(String emailId, String deviceId) throws UserNotFoundException {
        Map<String, Object> map = new HashMap<>();
        if (emailId == null || emailId.length() == 0) {
            map.put("deviceId", deviceId);
            String sql = " INSERT INTO user_device (device_id) VALUES (:deviceId) ON CONFLICT ON CONSTRAINT unique_device DO NOTHING ;";
            this.parameterJdbcTemplate.update(sql, map);
        } else {
            Long userId = userServiceUtility.findUserIdByEmail(emailId);
            map.put("userId", userId);
            map.put("deviceId", deviceId);
            String sql = "UPDATE user_dladle SET device_id=:deviceId WHERE id=:userId;";
            this.parameterJdbcTemplate.update(sql, map);
        }
    }

    public void invalidateDeviceId(String emailId) throws UserNotFoundException {
        Map<String, Object> map = new HashMap<>();
        Long userId = userServiceUtility.findUserIdByEmail(emailId);
        map.put("userId", userId);
        String sql = "UPDATE user_dladle SET device_id=NULL WHERE id=:userId;";
        this.parameterJdbcTemplate.update(sql, map);
    }

    public String uploadProfilePic(String base64Image) throws IOException, UserNotFoundException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        Long userId = userServiceUtility.findUserIdByEmail(userSession.getUser().getEmailId());

        String imageUrl = fileManagementService.upload(base64Image);
        Map<String, Object> map = new HashMap<>();
        map.put("profilePicture", imageUrl);
        map.put("userId", userId);
        String sql = "UPDATE user_dladle SET profile_picture=:profilePicture WHERE id=:userId;";
        this.parameterJdbcTemplate.update(sql, map);

        return imageUrl;
    }

    public User getDetails(String emailId) throws Exception {
        return userServiceUtility.findUserDetailsByEmail(emailId);
    }
}
