package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.entity.*;
import za.co.dladle.service.RatingService;
import za.co.dladle.util.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by prady on 4/16/2017.
 */
@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    //------------------------------------------------------------------------------------------------------------------
    //View Rating
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/rating/view", method = RequestMethod.GET)
    public Map<String, Object> viewRating() throws IOException {
        try {
            RatingView ratingView = ratingService.viewRating();
            return ResponseUtil.response("Success", ratingView, "Rate retrieved Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("Success", null, "Rate not available for above user");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //View Rating Details
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/rating/view/details", method = RequestMethod.GET)
    public Map<String, Object> viewRatingDetails() throws IOException {
        try {
            List<RatingViewDetails> ratingViewDetails = ratingService.viewRatingDetails();
            return ResponseUtil.response("Success", ratingViewDetails, "Rate retrieved Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("Success", null, "Rate not available for above user");
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Give Rating
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/rating/post", method = RequestMethod.POST)
    public Map<String, Object> postRating(@RequestBody(required = false) RatingAddRequest ratingAddRequest) throws IOException {
        try {
            boolean b = ratingService.postRating(ratingAddRequest);
            return ResponseUtil.response("Success", b, "Rate posted Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("Success", null, "Unable to post rating");
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    //Update rating
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/api/rating/update", method = RequestMethod.POST)
    public Map<String, Object> updateRating(@RequestBody(required = false) RatingUpdateRequest ratingUpdateRequest) throws IOException {
        try {
            boolean b = ratingService.updateRating(ratingUpdateRequest);
            return ResponseUtil.response("Success", b, "Rate updated Successfully");
        } catch (Exception e) {
            return ResponseUtil.response("Success", null, "Unable to uodate rating");
        }
    }

}
