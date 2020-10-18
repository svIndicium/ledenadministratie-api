package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyTypeDTO {
    private String shortName;

    private String name;

    public StudyTypeDTO(StudyType studyType) {
        this.shortName = studyType.getShortName();
        this.name = studyType.getName();
    }
}
