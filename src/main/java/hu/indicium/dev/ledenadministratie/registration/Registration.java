package hu.indicium.dev.ledenadministratie.registration;


import hu.indicium.dev.ledenadministratie.mail.MailObject;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registrations")
public class Registration implements MailObject {
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

    @Column(nullable = false)
    private String mailAddress;

    @Column()
    private String verificationToken;

    @Column()
    private Date verificationRequestedAt;

    @Column()
    private Date verifiedAt;

    @Column()
    private boolean receivesNewsletter;

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

    @Override
    public String getMailAddress() {
        return mailAddress;
    }

    @Override
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    @Override
    public Date getVerifiedAt() {
        return verifiedAt;
    }

    @Override
    public void verify() {
        this.verifiedAt = new Date();
    }

    @Override
    public Date getVerificationRequestedAt() {
        return verificationRequestedAt;
    }

    @Override
    public void setVerificationRequestedAt(Date verificationRequestedAt) {
        this.verificationRequestedAt = verificationRequestedAt;
    }

    @Override
    public boolean receivesNewsletter() {
        return receivesNewsletter;
    }

    @Override
    public void setReceivesNewsletter(boolean receivesNewsletter) {
        this.receivesNewsletter = receivesNewsletter;
    }

    @Override
    public String getVerificationToken() {
        return verificationToken;
    }

    @Override
    public void setVerificationToken(String token) {
        this.verificationToken = token;
    }
}

