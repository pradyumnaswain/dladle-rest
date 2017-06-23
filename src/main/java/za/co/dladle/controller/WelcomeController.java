package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.service.WelcomeService;
import za.co.dladle.util.ResponseUtil;

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
