package za.co.dladle.service;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
import za.co.dladle.thirdparty.FileManagementServiceCloudinaryImpl;
import za.co.dladle.thirdparty.NotificationServiceSendGridImpl;
import za.co.dladle.apiutil.RandomUtil;
import za.co.dladle.serviceutil.UserUtility;

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
    private UserUtility userUtility;

    @Autowired
    private RatingService ratingService;

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

        try {
            String sql = "SELECT * FROM user_dladle INNER JOIN user_type ON user_dladle.user_type_id = user_type.id WHERE emailid=? AND password=?";
            User u = this.jdbcTemplate.queryForObject(sql, new Object[]{user.getEmailId().toLowerCase(), hashedPassword}, (rs, rowNum) ->
                    new User(rs.getString("emailId"),
                            null,
                            rs.getBoolean("verified"),
                            UserType.valueOf(rs.getString("name").toUpperCase()),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("id_number"),
                            rs.getString("cell_number"),
                            rs.getString("profile_picture")));
            if (u.getUserType().eqVENDOR()) {
                String sql1 = "SELECT account_set FROM vendor INNER JOIN user_dladle ON vendor.user_id = user_dladle.id WHERE emailid=?";
                Boolean accountSet = this.jdbcTemplate.queryForObject(sql1, new Object[]{user.getEmailId().toLowerCase()}, Boolean.class);
                u.setAccountSet(accountSet);
            }
            return u;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Username or password is wrong. Please check and login again");
        }
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
        return userUtility.findUserByEmail(emailId);
    }

    //------------------------------------------------------------------------------------------------------------------
    //Reset Password
    //------------------------------------------------------------------------------------------------------------------
    public void resetPassword(UserRequestForResetPassword user) throws OtpMismatchException {
        String emailId = user.getEmailId();
        String hashedPassword = Hashing.sha512().hashString(user.getNewPassword(), Charset.defaultCharset()).toString();
        Map<String, Object> map = new HashMap<>();

        map.put("otp", user.getOtp());
        map.put("emailId", emailId.toLowerCase());
        map.put("password", hashedPassword);

        String sql = " UPDATE user_dladle SET password=:password WHERE emailid=:emailId AND otp=:otp";
        int rows = this.parameterJdbcTemplate.update(sql, map);
        if (rows == 1) {
            String sqlUpdate = " UPDATE user_dladle SET otp=NULL WHERE emailid=:emailId AND otp=:otp AND password=:password";
            this.parameterJdbcTemplate.update(sqlUpdate, map);
        } else {
            throw new OtpMismatchException("OTP doesn't match");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Change Password
    //------------------------------------------------------------------------------------------------------------------
    public void changePassword(ChangePasswordRequest changePassword) throws PasswordMismatchException {
        //UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        //String emailId = userSession.getUser().getEmailId();
        Map<String, Object> map = new HashMap<>();

        map.put("emailId", changePassword.getEmailId().toLowerCase());

        String sql = "SELECT password FROM user_dladle WHERE emailid=:emailId";
        String oldPassword = this.parameterJdbcTemplate.queryForObject(sql, map, String.class);
        String hashedOldPassword = Hashing.sha512().hashString(changePassword.getCurrentPassword(), Charset.defaultCharset()).toString();
        if (oldPassword.equals(hashedOldPassword)) {

            if (changePassword.getNewPassword().equals(changePassword.getNewConfirmPassword())) {
                String hashedPassword = Hashing.sha512().hashString(changePassword.getNewPassword(), Charset.defaultCharset()).toString();

                map.put("password", hashedPassword);

                String sql1 = " UPDATE user_dladle SET password=:password WHERE emailid=:emailId";
                this.parameterJdbcTemplate.update(sql1, map);
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

        String hashedCode = Hashing.sha1().hashString(user.getPassword(), Charset.defaultCharset()).toString();
        String verificationUrl = verificationLink + user.getEmailId() + "/" + hashedCode;

        int rows = userRegistration(user, hashedCode);

        if (rows == 1) {
            //send mail
            notificationServiceSendGridImpl.sendVerificationMail(user.getEmailId(), verificationUrl);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Verify User
    //------------------------------------------------------------------------------------------------------------------
    public void verify(String emailId, String verificationCode) throws IOException, UserVerificationCodeNotMatchException {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("emailId", emailId.toLowerCase())
                .addValue("verifyCode", verificationCode);
        try {
            String sql = "SELECT id FROM user_dladle WHERE emailid=:emailId AND verification_code=:verifyCode";
            this.parameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, (rs, rowNum) -> rs.getLong("id"));

            String sqlUpdate = "UPDATE user_dladle SET verified=TRUE WHERE emailid=:emailId AND verification_code=:verifyCode";
            this.parameterJdbcTemplate.update(sqlUpdate, mapSqlParameterSource);
            notificationServiceSendGridImpl.sendWelcomeMail(emailId);
        } catch (Exception e) {
            throw new UserVerificationCodeNotMatchException("Either EmailId or Verification Code for User doesn't match");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Send OTP for Forgot Password
    //------------------------------------------------------------------------------------------------------------------
    public void sendOtp(String emailId) throws IOException {
        Integer otp = RandomUtil.generateRandom();
        Map<String, Object> map = new HashMap<>();

        map.put("otp", otp);
        map.put("emailId", emailId.toLowerCase());

        String sql = " UPDATE user_dladle SET otp=:otp WHERE emailid=:emailId";
        this.parameterJdbcTemplate.update(sql, map);
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
        String vendorSql = " UPDATE vendor SET  service_type_id=:serviceType, business_address=:businessAddress, tools=:tools, transport=:transport, experience_id=:experience, account_set=TRUE WHERE user_id=(SELECT id FROM user_dladle WHERE emailid=:emailId)";
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
            Long userId = userUtility.findUserIdByEmail(emailId);
            map.put("userId", userId);
            map.put("deviceId", deviceId);
            String sql = "UPDATE user_dladle SET device_id=:deviceId WHERE id=:userId;";
            this.parameterJdbcTemplate.update(sql, map);
        }
    }

    public void invalidateDeviceId(String emailId) throws UserNotFoundException {
        Map<String, Object> map = new HashMap<>();
        Long userId = userUtility.findUserIdByEmail(emailId);
        map.put("userId", userId);
        String sql = "UPDATE user_dladle SET device_id=NULL WHERE id=:userId;";
        this.parameterJdbcTemplate.update(sql, map);
    }

    public String uploadProfilePic(String base64Image) throws IOException, UserNotFoundException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());

        String imageUrl = fileManagementService.upload(base64Image);
        Map<String, Object> map = new HashMap<>();
        map.put("profilePicture", imageUrl);
        map.put("userId", userId);
        String sql = "UPDATE user_dladle SET profile_picture=:profilePicture WHERE id=:userId;";
        this.parameterJdbcTemplate.update(sql, map);

        return imageUrl;
    }

    public User getDetails(String emailId) throws Exception {
        List<RatingViewDetails> ratingViewDetails = ratingService.viewRatingDetails(emailId);
        String sql = "SELECT * FROM user_dladle INNER JOIN user_type ON user_dladle.user_type_id = user_type.id WHERE emailid=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{emailId.toLowerCase()}, (rs, rowNum) ->
                new User(rs.getString("emailId"),
                        rs.getBoolean("verified"),
                        UserType.valueOf(rs.getString("name").toUpperCase()),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("id_number"),
                        rs.getString("cell_number"),
                        rs.getString("profile_picture"),
                        ratingViewDetails));
    }

    @javax.transaction.Transactional
    int userRegistration(UserRegisterRequest user, String hashedCode) throws UseAlreadyExistsException {

        String hashedPassword = Hashing.sha512().hashString(user.getPassword(), Charset.defaultCharset()).toString();

        Map<String, Object> map = new HashMap<>();
        map.put("emailId", user.getEmailId().toLowerCase());

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("emailId", user.getEmailId())
                .addValue("password", hashedPassword)
                .addValue("userType", UserTypeMapper.getUserType(user.getUserType()))
                .addValue("verified", false)
                .addValue("verifyCode", hashedCode)
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("idNumber", user.getIdentityNumber());


        String countSql = "SELECT COUNT(emailid) FROM user_dladle WHERE emailid=:emailId";

        Integer countEmail = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

        int rowsUpdated = 0;

        if (countEmail == 0) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            String UserSql = "INSERT INTO user_dladle (emailid, password, user_type_id, verified,verification_code,first_name,last_name,id_number) VALUES (:emailId,:password,:userType,:verified,:verifyCode,:firstName,:lastName,:idNumber)";

            this.parameterJdbcTemplate.update(UserSql, mapSqlParameterSource, keyHolder, new String[]{"id"});

            System.out.println(keyHolder.getKey());
            map.put("userId", keyHolder.getKey());

            if (user.getUserType().equals(UserType.LANDLORD)) {
                String LandlordSql = "INSERT INTO landlord(user_id) VALUES (:userId)";
                rowsUpdated = this.parameterJdbcTemplate.update(LandlordSql, map);
            }
            if (user.getUserType().equals(UserType.TENANT)) {
                String TenantSql = "INSERT INTO tenant (user_id) VALUES (:userId)";
                rowsUpdated = this.parameterJdbcTemplate.update(TenantSql, map);
            }
            if (user.getUserType().equals(UserType.VENDOR)) {
                String VendorSql = "INSERT INTO vendor (user_id) VALUES (:userId)";
                rowsUpdated = this.parameterJdbcTemplate.update(VendorSql, map);
            }
            return rowsUpdated;
        } else {
            throw new UseAlreadyExistsException("User already Exists");
        }
    }
}
