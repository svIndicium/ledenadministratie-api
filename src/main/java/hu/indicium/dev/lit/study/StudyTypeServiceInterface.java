package hu.indicium.dev.lit.study;

import java.util.List;

public interface StudyTypeServiceInterface {
    List<StudyType> getAllStudyTypes();

    StudyType getStudyTypeById(Long studyTypeId);

    StudyType createStudyType(StudyType studyType);

    StudyType updateStudyType(StudyType studyType);

    void deleteStudyType(Long studyTypeId);
}
