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
import za.co.dladle.apiutil.RandomUtil;
import za.co.dladle.entity.*;
import za.co.dladle.exception.*;
import za.co.dladle.mapper.ServiceTypeMapper;
import za.co.dladle.mapper.UserTypeMapper;
import za.co.dladle.mapper.YearsOfExperienceTypeMapper;
import za.co.dladle.model.User;
import za.co.dladle.model.UserType;
import za.co.dladle.serviceutil.UserUtility;
import za.co.dladle.session.UserSession;
import za.co.dladle.thirdparty.document.DocumentManagementServiceCloudinaryImpl;
import za.co.dladle.thirdparty.document.DocumentManagementServiceImpl;
import za.co.dladle.thirdparty.email.EmailServiceSendGridImpl;
import za.co.dladle.thirdparty.email.EmailServiceZohoMailImpl;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private EmailServiceZohoMailImpl notificationServiceSendGridImpl;

    @Autowired
    private DocumentManagementServiceImpl fileManagementService;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${verification.link}")
    private String verificationLink;

    @Value("${document.store.url}")
    private String documentUrl;

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
            String sql = "SELECT *,user_dladle.id user_id FROM user_dladle INNER JOIN user_type ON user_dladle.user_type_id = user_type.id WHERE emailid=? AND password=? AND verified=TRUE AND status=TRUE ";
            User u = this.jdbcTemplate.queryForObject(sql, new Object[]{user.getEmailId().toLowerCase(), hashedPassword}, (rs, rowNum) ->
                    new User(rs.getString("emailId"),
                            rs.getLong("user_id"),
                            null,
                            rs.getBoolean("verified"),
                            UserType.valueOf(rs.getString("name").toUpperCase()),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("id_number"),
                            rs.getString("cell_number"),
                            documentUrl + "profile/" + rs.getLong("user_id") + "/" + rs.getString("profile_picture"),
                            rs.getBoolean("payment_account_set")));
            try {
                String sql1 = "SELECT count FROM notification_count INNER JOIN user_dladle ON notification_count.user_id = user_dladle.id WHERE emailid=? AND house_id=0";
                Integer count = this.jdbcTemplate.queryForObject(sql1, new Object[]{user.getEmailId()}, Integer.class);
                u.setNotificationsCount(count);
            } catch (Exception e) {
                u.setNotificationsCount(0);
            }
            if (u.getUserType().eqVENDOR()) {
                String sql1 = "SELECT account_set,account_verified,service_type_id FROM vendor INNER JOIN user_dladle ON vendor.user_id = user_dladle.id WHERE emailid=?";
                VendorAccountStatus vendorAccountStatus = this.jdbcTemplate.queryForObject(sql1, new Object[]{user.getEmailId().toLowerCase()}, (rs1, rowNum1) -> new VendorAccountStatus(rs1.getBoolean("account_set"), rs1.getBoolean("account_verified"), rs1.getInt("service_type_id")));
                u.setAccountSet(vendorAccountStatus.isAccountSet());
                u.setAccountVerified(vendorAccountStatus.isAccountVerified());
                u.setServiceType(ServiceTypeMapper.getServiceType(vendorAccountStatus.getServiceType()));
            } else if (u.getUserType().eqTENANT()) {
                String sql1 = "SELECT valid FROM tenant_property_documents INNER JOIN tenant ON tenant_property_documents.tenant_id = tenant.id INNER JOIN user_dladle ON tenant.user_id = user_dladle.id WHERE emailid=?";
                List<Boolean> booleans = new ArrayList<>();
                this.jdbcTemplate.query(sql1, new Object[]{user.getEmailId().toLowerCase()}, (rs, rowNum) -> {
                    boolean a = rs.getBoolean("valid");
                    booleans.add(a);
                    return a;
                });
                if (booleans.contains(Boolean.TRUE)) {
                    u.setHouseStatus(true);
                } else {
                    u.setHouseStatus(false);
                }
                Long tenantId = userUtility.findTenantIdByEmail(u.getEmailId());
                String sqlPropertyJoin = "SELECT joined_date FROM lease_tenant WHERE tenant_id=? AND lease_status=TRUE ";

                try {
                    String propertyJoinedDate = this.jdbcTemplate.queryForObject(sqlPropertyJoin, new Object[]{tenantId}, String.class);
                    u.setTenantPropertyJoinedDate(propertyJoinedDate);
                } catch (Exception e) {
                    u.setTenantPropertyJoinedDate(null);
                }
            }
            String sqlLogin = "UPDATE user_dladle SET last_logged_in_date=? WHERE emailid=?";
            this.jdbcTemplate.update(sqlLogin, LocalDateTime.now(), user.getEmailId().toLowerCase());
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
    public void verify(String emailId, String verificationCode) throws UserVerificationCodeNotMatchException {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("emailId", emailId.toLowerCase())
                .addValue("verifyCode", verificationCode);
        try {
            String sql = "SELECT id FROM user_dladle WHERE emailid=:emailId AND verified=TRUE ";
            this.parameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, (rs, rowNum) -> rs.getLong("id"));
        } catch (Exception e) {
            throw new UserVerificationCodeNotMatchException("You are already verified");
        }
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
        // TODO: 7/5/2017 Send Email to AGKB
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

    public String uploadProfilePic(ProfilePictureUploadRequest base64Image) throws IOException, UserNotFoundException {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        Long userId = userUtility.findUserIdByEmail(userSession.getUser().getEmailId());

        String imageUrl = fileManagementService.uploadPhoto(userSession.getUser().getUserId().toString(), "profile", base64Image.getBase64Image(), base64Image.getFileName());
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

    @Transactional
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
                .addValue("idNumber", user.getIdentityNumber())
                .addValue("registeredDate", LocalDateTime.now());


        String countSql = "SELECT COUNT(emailid) FROM user_dladle WHERE emailid=:emailId";

        Integer countEmail = this.parameterJdbcTemplate.queryForObject(countSql, map, Integer.class);

        int rowsUpdated = 0;

        if (countEmail == 0) {

            KeyHolder keyHolder = new GeneratedKeyHolder();

            String UserSql = "INSERT INTO user_dladle (emailid, password, user_type_id, verified,verification_code,first_name,last_name,id_number,rgistered_date) VALUES " +
                    "(:emailId,:password,:userType,:verified,:verifyCode,:firstName,:lastName,:idNumber,:registeredDate)";

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
        } else if (countEmail == 1) {
            String countStatusSql = "SELECT status FROM user_dladle WHERE emailid=:emailId AND status=TRUE ";

            Boolean status = this.parameterJdbcTemplate.queryForObject(countStatusSql, map, Boolean.class);

            if (status) {
                throw new UseAlreadyExistsException("User already Exists");
            } else {
                throw new UseAlreadyExistsException("User already Exists");
            }
        } else {
            throw new UseAlreadyExistsException("User already Exists");
        }
    }

    public Boolean deleteUser(UserDeleteRequest deleteRequest) {

        UserSession userSession = applicationContext.getBean(UserSession.class);
        Map<String, Object> map = new HashMap<>();
        map.put("password", Hashing.sha512().hashString(deleteRequest.getPassword(), Charset.defaultCharset()).toString());
        map.put("emailId", userSession.getUser().getEmailId());
        map.put("deletedEmailId", "deleted-" + userSession.getUser().getEmailId() + "-" + LocalDateTime.now().toString());
        map.put("deletedDate", LocalDateTime.now());

        String sql = "UPDATE user_dladle SET status=FALSE , emailid=:deletedEmailId, deleted_date=:deletedDate WHERE emailid=:emailId AND password=:password";

        int deleted = this.parameterJdbcTemplate.update(sql, map);
        session.invalidate();
        return deleted != 0;
    }

    public boolean validateUser(String password) {
        UserSession userSession = applicationContext.getBean(UserSession.class);
        Map<String, Object> map = new HashMap<>();
        map.put("password", Hashing.sha512().hashString(password, Charset.defaultCharset()).toString());
        map.put("emailId", userSession.getUser().getEmailId());

        String sql = "SELECT count(*) FROM user_dladle WHERE emailid=:emailId AND password=:password";

        int count = this.parameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return count == 1;
    }
}
