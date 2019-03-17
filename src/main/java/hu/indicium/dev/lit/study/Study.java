package hu.indicium.dev.lit.study;

import hu.indicium.dev.lit.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Study {
    @Id
    @SequenceGenerator(name = "study_id_generator", sequenceName = "study_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "study_id_generator")
    private Long id;

    @Column(nullable = false)
    private Date startDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_study")
    private StudyType type;

    public Study(Date startDate, User user, StudyType type) {
        this.startDate = startDate;
        this.user = user;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StudyType getType() {
        return type;
    }

    public void setType(StudyType type) {
        this.type = type;
    }
}
