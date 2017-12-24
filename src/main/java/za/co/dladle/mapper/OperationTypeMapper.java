package za.co.dladle.mapper;

import za.co.dladle.model.OperationType;

/**
 * Created by prady on 4/1/2017.
 */
public class OperationTypeMapper {
    public static Integer getOperationType(OperationType operationType) {
        switch (operationType) {
            case KEY_GENERATION:
                return 1;
            case SERVICE_FEE:
                return 2;
            default:
                return null;
        }
    }

    public static String getOperationType(int id) {
        switch (id) {
            case 1:
                return "KEY GENERATION";
            case 2:
                return "SERVICE FEE";
            default:
                return null;
        }
    }
}
