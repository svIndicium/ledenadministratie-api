package hu.indicium.dev.ledenadministratie.domain.model.user;

import hu.indicium.dev.ledenadministratie.domain.AssertionConcern;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Embeddable
@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberDetails extends AssertionConcern {

    @Embedded
    private Name name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "study_type_id")
    private StudyType studyType;

    public MemberDetails(Name name, String phoneNumber, Date dateOfBirth, StudyType studyType) {
        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setDateOfBirth(dateOfBirth);
        this.studyType = studyType;
        this.createdAt = new Date();
    }

    public void setName(Name name) {
        assertArgumentNotNull(name, "Name must be given.");

        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        assertArgumentNotNull(phoneNumber, "Phone number must be given.");

        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        assertArgumentNotNull(dateOfBirth, "Date of birth must be given.");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 15);

        assertArgumentTrue(calendar.getTime().after(dateOfBirth), "Date of birth must be at least 15 years ago.");

        this.dateOfBirth = dateOfBirth;
    }

    public void setStudyType(StudyType studyType) {
        assertArgumentNotNull(studyType, "Studytype must be given.");

        this.studyType = studyType;
    }
}
