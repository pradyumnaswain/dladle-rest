package za.co.dladle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

/**
 * Created by Pradyumna on 6/13/2016.
 */
@SpringBootApplication
public class Sandbox {

    @Autowired
    private UserSessionMonitor sessionMonitor;

//    private static EmailServiceZohoMailImpl notificationServiceZohoMail;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Sandbox.class);
//        notificationServiceZohoMail = applicationContext.getBean(EmailServiceZohoMailImpl.class);
//        notificationServiceZohoMail.sendWelcomeMail("pradyumnaswain76@gmail.com");
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return sessionMonitor;
    }
}
