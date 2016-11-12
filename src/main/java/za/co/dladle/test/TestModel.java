package za.co.dladle.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.dladle.model.User;
import za.co.dladle.model.UserType;
import za.co.dladle.repository.UserRepository;

/**
 * Created by prady on 9/3/2016.
 */
@Component
public class TestModel {
    @Autowired
    UserRepository userRepository;

    public void save(){
        User user=new User();

        user.setEmail("pradyumnaswain761@gmail.com");

        user.setPassword("XXXX");

        user.setUserType(UserType.TENANT());

        user.setVerified(Boolean.TRUE);

        userRepository.save(user);

    }
}
