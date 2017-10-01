package za.co.dladle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import za.co.dladle.entity.VendorResponse;
import za.co.dladle.service.VendorService;

import javax.servlet.http.HttpSessionListener;

/**
 * Created by Pradyumna on 6/13/2016.
 */
@SpringBootApplication
public class Sandbox {

    @Autowired
    private UserSessionMonitor sessionMonitor;

//    private static VendorService vendorService;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Sandbox.class);
//        vendorService = applicationContext.getBean(VendorService.class);
//        VendorResponse rightVendor = vendorService.findRightVendor(20L);
//        System.out.println(rightVendor);
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return sessionMonitor;
    }
}
