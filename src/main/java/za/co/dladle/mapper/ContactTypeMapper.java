package za.co.dladle.mapper;

import za.co.dladle.model.ContactType;

/**
 * Created by prady on 4/1/2017.
 */
public class ContactTypeMapper {
    public static Integer getPropertyContactType(ContactType contactType) {
        switch (contactType) {
            case HOSPITAL:
                return 1;
            case ELECTRICIAN:
                return 2;
            default:
                return null;
        }
    }
}
