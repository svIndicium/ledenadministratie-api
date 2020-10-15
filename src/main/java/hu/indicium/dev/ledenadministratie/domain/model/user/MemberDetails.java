package hu.indicium.dev.ledenadministratie.domain.model.user;

import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Embeddable
@NoArgsConstructor
@Getter
public class MemberDetails {

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
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.studyType = studyType;
        this.createdAt = new Date();
    }
}
