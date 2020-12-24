package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StudyTypeDto {
    private UUID id;

    private String shortName;

    private String name;

    public StudyTypeDto(StudyType studyType) {
        this.id = studyType.getStudyTypeId().getId();
        this.shortName = studyType.getShortName();
        this.name = studyType.getName();
    }
}
