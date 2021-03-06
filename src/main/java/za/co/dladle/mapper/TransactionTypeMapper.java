package za.co.dladle.mapper;

import za.co.dladle.model.TransactionType;

/**
 * Created by prady on 4/1/2017.
 */
public class TransactionTypeMapper {
    public static Integer getTransactionType(TransactionType transactionType) {
        switch (transactionType) {
            case CREDIT:
                return 1;
            case DEBIT:
                return 2;
            default:
                return null;
        }
    }

    public static String getTransactionType(int id) {
        switch (id) {
            case 1:
                return "CREDIT";
            case 2:
                return "DEBIT";
            default:
                return null;
        }
    }
}
