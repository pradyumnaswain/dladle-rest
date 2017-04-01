package za.co.dladle.service;

import org.springframework.stereotype.Service;
import za.co.dladle.model.*;

import java.util.*;

/**
 * Created by prady on 4/1/2017.
 */
@Service
public class SelectionService {
    public Map<String, String> getHomeViewTypes() {
        Map<String, String> map = new HashMap<>();

        for (HomeViewType homeViewType : HomeViewType.values()) {
            map.put(homeViewType.getId(), homeViewType.name());
        }
        return map;
    }

    public Map<String, String> getPlaceTypeValues() {
        Map<String, String> map = new HashMap<>();

        for (PlaceType placeType : PlaceType.values()) {
            map.put(placeType.getId(), placeType.name());
        }
        return map;
    }

    public Map<String, String> getUserTypes() {
        Map<String, String> map = new HashMap<>();

        for (UserType userType : UserType.values()) {
            map.put(userType.getId(), userType.name());
        }
        return map;
    }

    public Map<String, String> getVendorServiceTypeValues() {
        Map<String, String> map = new HashMap<>();

        for (ServiceType serviceType : ServiceType.values()) {
            map.put(serviceType.getId(), serviceType.name());
        }
        return map;
    }

    public Map<String, String> getYearsOfExperienceTypeValues() {
        Map<String, String> map = new HashMap<>();

        for (YearsExperience yearsExperience : YearsExperience.values()) {
            map.put(yearsExperience.getId(), yearsExperience.name());
        }
        return map;
    }
}
