package za.co.dladle.service;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.dladle.exception.UseAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.exception.UserVerificationCodeNotMatchException;
import za.co.dladle.model.BusinessType;
import za.co.dladle.model.User;
import za.co.dladle.model.UserRegisterRequest;
import za.co.dladle.model.UserType;
import za.co.dladle.session.UserSession;

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


    @Transactional
    public User setSessionService(User user) {
        session.setAttribute("user", user);

        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);

        userSession.setUser(user);

        return user;
    }

    public User login(User user) throws UserNotFoundException {
        String hashedPassword = Hashing.sha512().hashString(user.getPassword(), Charset.defaultCharset()).toString();

        user.setPassword(hashedPassword);

        return userServiceUtility.findUserByEmailAndPassword(user.getEmailId(), hashedPassword);
    }

    public void logout() {
        session.invalidate();
    }

    public User forgotPassword(String emailId) throws UserNotFoundException {
        return userServiceUtility.findUserByEmail(emailId);
    }

    public void resetPassword(User user) {
        String emailId = user.getEmailId();
        String hashedPassword = Hashing.sha512().hashString(user.getPassword(), Charset.defaultCharset()).toString();
        userServiceUtility.updateUserPassword(emailId, hashedPassword);
    }

    public void changePassword(String newPassword) {
        UserSession userSession = applicationContext.getBean("userSession", UserSession.class);
        String emailId = userSession.getUser().getEmailId();
        String hashedPassword = Hashing.sha512().hashString(newPassword, Charset.defaultCharset()).toString();
        userServiceUtility.updateUserPassword(emailId, hashedPassword);
    }

    @Transactional
    public void register(UserRegisterRequest user) throws UseAlreadyExistsException, IOException {

        String hashedCode = Hashing.sha1().hashString(user.getPassword(), Charset.defaultCharset()).toString();
        verificationLink = verificationLink + user.getEmailId() + "/" + hashedCode;
        int rows = userServiceUtility.userRegistration(user, hashedCode);

        if (rows == 1) {
            //send mail
            notificationService.sendMail(user.getEmailId(), verificationLink);
        }
    }

    public void verify(String emailId, String verificationCode) throws IOException, UserVerificationCodeNotMatchException {

        userServiceUtility.updateUserVerification(emailId, verificationCode);
    }
}
