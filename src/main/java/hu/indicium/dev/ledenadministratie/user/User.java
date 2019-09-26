package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.studytype.StudyType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
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

    public User() {
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

    public boolean isToReceiveNewsletter() {
        return isToReceiveNewsletter;
    }

    public void setToReceiveNewsletter(boolean toReceiveNewsletter) {
        isToReceiveNewsletter = toReceiveNewsletter;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }
}
