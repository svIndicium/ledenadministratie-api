package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;

import java.util.List;

public interface StudyTypeService {
    StudyTypeDTO getStudyTypeById(Long studyTypeId);

    List<StudyTypeDTO> getAllStudyTypes();

    StudyTypeDTO createStudyType(StudyTypeDTO studyType);

    boolean isNameInUse(String studyTypeName);
}
