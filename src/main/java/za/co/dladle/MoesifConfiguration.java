package za.co.dladle;

import com.moesif.api.models.EventModel;
import com.moesif.servlet.MoesifFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import za.co.dladle.model.User;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by prady on 9/10/2017.
 */
@Configuration
public class MoesifConfiguration extends WebMvcConfigurerAdapter implements com.moesif.servlet.MoesifConfiguration {
    @Value("${moesif.api.key}")
    private String apiKey;

    @Bean
    public Filter moesifFilter() {
        return new MoesifFilter(apiKey);
    }

    @Override
    public boolean skip(HttpServletRequest httpRequest, HttpServletResponse response) {
        // Skip logging health probes
        return httpRequest.getRequestURI().contains("/swagger-ui.html")
                || httpRequest.getRequestURI().contains("/v2/api-docs")
                || httpRequest.getRequestURI().contains("/swagger-resources")
                || httpRequest.getRequestURI().contains("/configuration/ui")
                || httpRequest.getRequestURI().contains("/configuration/security")
                || httpRequest.getRequestURI().matches("/webjars/(.*)");
    }

    @Override
    public EventModel maskContent(EventModel eventModel) {
        return null;
    }

    @Override
    public String identifyUser(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession() == null) {
            return null;
        } else {
            return request.getUserPrincipal().getName();
        }
    }

    @Override
    public String getSessionToken(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public String getTags(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public String getApiVersion(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
