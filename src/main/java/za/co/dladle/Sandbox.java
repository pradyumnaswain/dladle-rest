package za.co.dladle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

/**
 * Created by Pradyumna on 6/13/2016.
 */
@SpringBootApplication
public class Sandbox {

    @Autowired
    private UserSessionMonitor sessionMonitor;

    public static void main(String[] args) {
        SpringApplication.run(Sandbox.class);
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return sessionMonitor;
    }
}
