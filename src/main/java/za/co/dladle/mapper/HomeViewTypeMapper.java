package za.co.dladle.mapper;

import za.co.dladle.model.HomeViewType;

/**
 * Created by prady on 4/1/2017.
 */
public class HomeViewTypeMapper {
    public static Integer getHomeViewType(HomeViewType homeViewType) {
        switch (homeViewType) {
            case HOME_VIEW_TYPE_1:
                return 1;
            case HOME_VIEW_TYPE_2:
                return 2;
            case HOME_VIEW_TYPE_3:
                return 3;
            default:
                return null;
        }
    }
}
