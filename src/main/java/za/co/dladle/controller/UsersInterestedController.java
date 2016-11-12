package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.model.UsersInterested;
import za.co.dladle.repository.UsersInterestedRepository;
import za.co.dladle.service.UsersInterestedService;

@RestController
public class UsersInterestedController {

    @Autowired
    UsersInterestedService usersInterestedService;

    @RequestMapping(value = "/emailId",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersInterested> saveUsersInterested(@RequestBody String emailId) {

        boolean contains = usersInterestedService.checkUserExists(emailId);

        if (contains) {
//            return new ResponseEntity<UsersInterested>(, HttpStatus.CONFLICT);
            return null;
        } else {
            UsersInterested usersInterested = usersInterestedService.saveUser(emailId);
            return new ResponseEntity<UsersInterested>(usersInterested, HttpStatus.CREATED);
        }
    }
}
