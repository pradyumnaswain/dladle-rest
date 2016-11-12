package za.co.dladle.model;

import javax.persistence.*;

@Entity(name = "users_interested")
public class UsersInterested {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email_id")
    private String emailId;

    public UsersInterested() {
    }

    public UsersInterested(String emailId) {
        this.emailId = emailId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
