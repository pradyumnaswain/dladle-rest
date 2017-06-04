package za.co.dladle.entity;

import za.co.dladle.model.Contact;

import java.util.List;

/**
 * Created by prady on 6/4/2017.
 */
public class ContactAddRequest {
    private long houseId;
    private List<Contact> contactList;

    public ContactAddRequest() {
    }

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
