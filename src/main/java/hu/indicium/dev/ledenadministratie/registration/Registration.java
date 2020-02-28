package hu.indicium.dev.ledenadministratie.registration;


import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registrations")
public class Registration {
    @Id
    @SequenceGenerator(name = "registration_id_generator", sequenceName = "registration_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_id_generator")
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column()
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private boolean isToReceiveNewsletter;

    @ManyToOne
    @JoinColumn(name = "study_type_id", nullable = false)
    private StudyType studyType;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;

    @Column()
    private Date finalizedAt;

    @Column()
    private String finalizedBy;

    @Column()
    private boolean approved;

    @Column()
    private String comment;

    public Registration() {
        //  Public no-args constructor for Hibernate
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }

    public boolean isToReceiveNewsletter() {
        return isToReceiveNewsletter;
    }

    public void setToReceiveNewsletter(boolean toReceiveNewsletter) {
        isToReceiveNewsletter = toReceiveNewsletter;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getFinalizedAt() {
        return finalizedAt;
    }

    public void setFinalizedAt(Date finalizedAt) {
        this.finalizedAt = finalizedAt;
    }

    public String getFinalizedBy() {
        return finalizedBy;
    }

    public void setFinalizedBy(String finalizedBy) {
        this.finalizedBy = finalizedBy;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

