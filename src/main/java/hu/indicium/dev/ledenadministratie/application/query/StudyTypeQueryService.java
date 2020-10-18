package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;

public interface StudyTypeQueryService {
    StudyType getStudyTypeById(StudyTypeId studyTypeId);
}
