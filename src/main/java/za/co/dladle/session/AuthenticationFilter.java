package za.co.dladle.session;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import za.co.dladle.model.User;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by prady on 11/13/2016.
 */
@Component
public class AuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            if (isLoginEndpoint(httpServletRequest) ||
                    isForgotPasswordEndpoint(httpServletRequest) ||
                    isResestPasswordEndpoint(httpServletRequest) ||
                    isSwaggerEndpoint(httpServletRequest)) {
                chain.doFilter(httpServletRequest, httpServletResponse);
            } else try {
                throw new AuthenticationException("User not logged in");
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        } else {
            chain.doFilter(request, response);
        }
        httpServletResponse.addHeader("Set-Cookie", "JSESSIONID=" + session.getId());
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "true");
    }

    private boolean isLoginEndpoint(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().contains("/api/user/login");
    }

    private boolean isForgotPasswordEndpoint(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().contains("/api/user/forgot-password");
    }

    private boolean isResestPasswordEndpoint(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI().contains("/api/user/reset-password");
    }

    private static boolean isSwaggerEndpoint(HttpServletRequest httpRequest) {
        return httpRequest.getRequestURI().contains("/swagger-ui.html")
                || httpRequest.getRequestURI().contains("/v2/api-docs")
                || httpRequest.getRequestURI().contains("/swagger-resources")
                || httpRequest.getRequestURI().contains("/configuration/ui")
                || httpRequest.getRequestURI().contains("/configuration/security")
                || httpRequest.getRequestURI().matches("/webjars/(.*)");
    }
}
