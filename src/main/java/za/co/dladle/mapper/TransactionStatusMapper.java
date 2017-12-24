package za.co.dladle.mapper;

import za.co.dladle.model.TransactionStatus;

/**
 * Created by prady on 4/1/2017.
 */
public class TransactionStatusMapper {
    public static Integer getTransactionStatus(TransactionStatus transactionStatus) {
        switch (transactionStatus) {
            case SUCCESS:
                return 1;
            case FAIL:
                return 2;
            case PENDING:
                return 3;
            case UNKNOWN:
                return 4;
            default:
                return null;
        }
    }

    public static String getTransactionStatus(int id) {
        switch (id) {
            case 1:
                return "SUCCESS";
            case 2:
                return "FAIL";
            case 3:
                return "PENDING";
            case 4:
                return "UNKNOWN";
            default:
                return null;
        }
    }
}
