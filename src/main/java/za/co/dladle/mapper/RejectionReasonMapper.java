package za.co.dladle.mapper;

import za.co.dladle.model.PlaceType;
import za.co.dladle.model.RejectionReason;

/**
 * Created by prady on 4/1/2017.
 */
public class RejectionReasonMapper {
    public static Integer getRejectionReasonType(RejectionReason rejectionReason) {
        switch (rejectionReason) {
            case PRICE_HIGH:
                return 1;
            case VENDOR_INAPPROPRIATE:
                return 2;
            case VENDOR_INEXPERIENCED:
                return 3;
            case VENDOR_RATING_BAD:
                return 4;
            case OTHER:
                return 5;
            default:
                return null;
        }
    }
}
