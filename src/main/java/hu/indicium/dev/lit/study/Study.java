package hu.indicium.dev.lit.study;

import hu.indicium.dev.lit.userdata.UserData;

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
    private UserData userData;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_study")
    private StudyType type;

    public Study(Date startDate, UserData userData, StudyType type) {
        this.startDate = startDate;
        this.userData = userData;
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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public StudyType getType() {
        return type;
    }

    public void setType(StudyType type) {
        this.type = type;
    }
}
