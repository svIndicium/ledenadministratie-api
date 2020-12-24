package hu.indicium.dev.ledenadministratie.domain.model.studytype;

import java.util.Collection;

public interface StudyTypeRepository {
    StudyTypeId nextIdentity();

    StudyType getStudyTypeById(StudyTypeId studyTypeId);

    StudyType save(StudyType studyType);

    Collection<StudyType> getAllStudyTypes();
}
