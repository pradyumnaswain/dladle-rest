package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.dladle.service.FileManagementServiceCloudinaryImpl;
import za.co.dladle.service.WelcomeService;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by prady on 1/14/2017.
 */
@RestController
public class WelcomeController {

    @Autowired
    private WelcomeService welcomeService;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public Map<String, Object> welcome() {
        String welcome = welcomeService.welcome();
        return ResponseUtil.response("SUCCESS", welcome, welcome);
    }
}
