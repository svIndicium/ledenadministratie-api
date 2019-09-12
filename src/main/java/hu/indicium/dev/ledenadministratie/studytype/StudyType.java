package hu.indicium.dev.ledenadministratie.studytype;

import javax.persistence.*;

@Entity
public class StudyType {

    @Id
    @SequenceGenerator(name = "study_type_id_generator", sequenceName = "study_type_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "study_type_id_generator")
    private Long id;

    private String name;

    public StudyType() {
    }

    public StudyType(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
