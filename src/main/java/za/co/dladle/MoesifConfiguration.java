package za.co.dladle;

import com.moesif.servlet.MoesifFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

/**
 * Created by prady on 9/10/2017.
 */
@Configuration
public class MoesifConfiguration extends WebMvcConfigurerAdapter {
    @Value("${moesif.api.key}")
    private String apiKey;

    @Bean
    public Filter moesifFilter() {
        return new MoesifFilter(apiKey);
    }
}
