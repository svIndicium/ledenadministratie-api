package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.springframework.stereotype.Service;

@Service
public class StudyTypeServiceImpl implements StudyTypeService {
    @Override
    public StudyTypeDTO getStudyTypeById(Long studyTypeId) {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);
        return StudyTypeMapper.toDTO(studyType);
    }
}
