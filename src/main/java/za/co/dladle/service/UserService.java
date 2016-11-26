package za.co.dladle.service;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.dladle.exception.UseAlreadyExistsException;
import za.co.dladle.exception.UserNotFoundException;
import za.co.dladle.model.User;
import za.co.dladle.model.UserRegisterRequest;
import za.co.dladle.session.UserSession;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
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

    public void register(UserRegisterRequest user) throws UseAlreadyExistsException {
        String firstName = user.getFirst_name();
        String lastName = user.getLast_name();
        String emailId = user.getEmailId();
        String password = user.getPassword();
        Integer user_type = user.getUser_type();
        String identityNumber = user.getIdentity_number();
        Boolean verified = user.isVerified();
        String address = user.getAddress();
        String business_type = user.getBusiness_type();
        String hashedPassword = Hashing.sha512().hashString(password, Charset.defaultCharset()).toString();
        userServiceUtility.userRegistration(firstName, lastName, emailId, hashedPassword, user_type, identityNumber, verified, address, business_type);
    }
}
