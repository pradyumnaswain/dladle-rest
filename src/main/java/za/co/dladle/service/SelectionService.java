package za.co.dladle.service;

import org.springframework.stereotype.Service;
import za.co.dladle.model.*;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> getRejectionReasonValues() {
        Map<String, String> map = new HashMap<>();

        for (RejectionReason rejectionReason : RejectionReason.values()) {
            map.put(rejectionReason.getId(), rejectionReason.name());
        }
        return map;
    }

    public Map<String, String> getContactTypes() {
        Map<String, String> map = new HashMap<>();

        for (ContactType contactType : ContactType.values()) {
            map.put(contactType.getId(), contactType.name());
        }
        return map;
    }
}
