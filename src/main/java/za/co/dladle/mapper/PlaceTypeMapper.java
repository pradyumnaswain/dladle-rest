package za.co.dladle.mapper;

import za.co.dladle.model.PlaceType;

/**
 * Created by prady on 4/1/2017.
 */
public class PlaceTypeMapper {
    public static Integer getPlaceType(PlaceType placeType) {
        switch (placeType) {
            case FLAT:
                return 1;
            case HOUSE:
                return 3;
            case TOWN_HOUSE:
                return 2;
            default:
                return null;
        }
    }
}
