package za.co.dladle.jpamodel;

import javax.persistence.*;

/**
 * Created by prady on 10/2/2017.
 */
@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private UserDladle user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDladle getUser() {
        return user;
    }

    public void setUser(UserDladle user) {
        this.user = user;
    }
}
