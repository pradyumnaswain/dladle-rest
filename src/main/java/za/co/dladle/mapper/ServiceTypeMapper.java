package za.co.dladle.mapper;

import za.co.dladle.model.ServiceType;

/**
 * Created by prady on 4/1/2017.
 */
public class ServiceTypeMapper {
    public static Integer getServiceType(ServiceType serviceType) {
        switch (serviceType) {
            case ELECTRICIAN:
                return 2;
            case PLUMBER:
                return 1;
            case PAINTER:
                return 3;
            default:
                return null;
        }
    }
}
