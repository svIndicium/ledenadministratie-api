package hu.indicium.dev.ledenadministratie.domain.model.studytype;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudyType {

    @EmbeddedId
    private StudyTypeId studyTypeId;

    private String shortName;

    private String name;

    public StudyType(StudyTypeId studyTypeId, String shortName, String name) {
        this.studyTypeId = studyTypeId;
        this.shortName = shortName;
        this.name = name;
    }
}
