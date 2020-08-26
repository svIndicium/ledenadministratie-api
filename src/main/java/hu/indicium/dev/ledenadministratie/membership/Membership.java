package hu.indicium.dev.ledenadministratie.membership;

import hu.indicium.dev.ledenadministratie.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Membership {
    @Id
    @SequenceGenerator(name = "membership_id_generator", sequenceName = "membership_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_id_generator")
    private Long id;

    private Date startDate;

    private Date endDate;

    @ManyToOne(optional = false)
    private User user;

    public Membership() {
        //  Public no-args constructor for Hibernate
    }

    public Membership(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return this.endDate.before(new Date());
    }
}
