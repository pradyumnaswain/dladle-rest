package za.co.dladle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import za.co.dladle.model.UsersInterested;
import za.co.dladle.repository.UsersInterestedRepository;

@Service
public class UsersInterestedService {

    @Autowired
    UsersInterestedRepository usersInterestedRepository;

    public boolean checkUserExists(String emailId) {
        return usersInterestedRepository.findAll()
                .stream()
                .anyMatch(x -> x.getEmailId().equalsIgnoreCase(emailId));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UsersInterested saveUser(String emailId) {
        UsersInterested usersInterested = new UsersInterested(emailId);

        return usersInterestedRepository.save(usersInterested);

    }
}
