package za.co.dladle.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import za.co.dladle.model.User;

/**
 * Created by prady on 11/13/2016.
 */
@Scope("session")
@Component
public class UserSession {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
