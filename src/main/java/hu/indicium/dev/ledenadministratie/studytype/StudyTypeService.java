package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;

public interface StudyTypeService {
    StudyTypeDTO getStudyTypeById(Long studyTypeId);
}
