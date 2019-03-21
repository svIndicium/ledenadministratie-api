package hu.indicium.dev.lit.study;

import javax.persistence.*;
import java.util.Set;

@Entity
public class StudyType {
    @Id
    @SequenceGenerator(name = "study_type_id_generator", sequenceName = "study_type_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "study_type_id_generator")
    private Long id;

    @Column(nullable = false)
    private String shortName;

    @Column(nullable = false)
    private String longName;

    @OneToMany(mappedBy = "type")
    private Set<Study> studies;

    protected StudyType() {
    }

    public StudyType(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
