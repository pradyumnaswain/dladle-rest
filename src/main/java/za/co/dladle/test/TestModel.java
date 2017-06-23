package za.co.dladle.test;

import org.springframework.stereotype.Component;
import za.co.dladle.model.User;

/**
 * Created by prady on 9/3/2016.
 */
@Component
public class TestModel {

    public void save() {
        User user = new User();

        user.setEmailId("pradyumnaswain761@gmail.com");

        user.setPassword("XXXX");

//        userRepository.save(user);

    }
}
