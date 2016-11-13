package za.co.dladle;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by prady on 11/13/2016.
 */
@Component
public class UserSessionMonitor implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession();
    }
}
