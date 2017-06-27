package za.co.dladle.mapper;

import za.co.dladle.model.ServiceStatus;

/**
 * Created by prady on 4/1/2017.
 */
public class ServiceStatusMapper {
    public static Integer getServiceStatus(ServiceStatus serviceStatus) {
        switch (serviceStatus) {
            case REQUESTED:
                return 1;
            default:
                return null;
        }
    }
}
