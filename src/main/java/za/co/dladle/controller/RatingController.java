package za.co.dladle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.dladle.entity.RatingAddRequest;
import za.co.dladle.entity.RatingUpdateRequest;
import za.co.dladle.entity.RatingView;
import za.co.dladle.entity.RatingViewDetails;
import za.co.dladle.service.RatingService;
import za.co.dladle.util.ApiConstants;
import za.co.dladle.util.DladleConstants;
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
    @RequestMapping(value = ApiConstants.RATING_VIEW, method = RequestMethod.GET)
    public Map<String, Object> viewRating() throws IOException {
        try {
            RatingView ratingView = ratingService.viewRating();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, ratingView, DladleConstants.RATING_VIEW);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //View Rating Details
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.RATING_VIEW_DETAILS, method = RequestMethod.GET)
    public Map<String, Object> viewRatingDetails() throws IOException {
        try {
            List<RatingViewDetails> ratingViewDetails = ratingService.viewRatingDetails();
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, ratingViewDetails, DladleConstants.RATING_VIEW_DETAILS);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Give Rating
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.RATING_POST, method = RequestMethod.POST)
    public Map<String, Object> postRating(@RequestBody(required = false) RatingAddRequest ratingAddRequest) throws IOException {
        try {
            boolean b = ratingService.postRating(ratingAddRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, b, DladleConstants.RATING_POST);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //Update rating
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = ApiConstants.RATING_UPDATE, method = RequestMethod.POST)
    public Map<String, Object> updateRating(@RequestBody(required = false) RatingUpdateRequest ratingUpdateRequest) throws IOException {
        try {
            boolean b = ratingService.updateRating(ratingUpdateRequest);
            return ResponseUtil.response(DladleConstants.SUCCESS_RESPONSE, b, DladleConstants.RATING_UPDATE);
        } catch (Exception e) {
            return ResponseUtil.response(DladleConstants.FAILURE_RESPONSE, null, e.getMessage());
        }
    }

}
