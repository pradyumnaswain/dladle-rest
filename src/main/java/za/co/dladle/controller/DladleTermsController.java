package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.service.DladleTermsService;
import za.co.dladle.util.ResponseUtil;

import java.util.Map;

/**
 * Created by prady on 1/14/2017.
 */
@RestController
public class DladleTermsController {
    @Autowired
    DladleTermsService termsService;

    @RequestMapping(value = "/terms", method = RequestMethod.GET)
    public Map<String, Object> terms() {
        String terms = termsService.getTerms();
        return ResponseUtil.response("Success", terms, "Terms & Conditions");
    }

}
