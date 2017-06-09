package za.co.dladle.entity;

/**
 * Created by prady on 6/9/2017.
 */
public class DeleteContactRequest {
    long contactId;

    public DeleteContactRequest() {
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }
}
