package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class StudyTypeMapper {

    public StudyTypeDTO toDTO(StudyType studyType) {
        StudyTypeDTO dto = new StudyTypeDTO();
        dto.setId(studyType.getId());
        dto.setName(studyType.getName());
        return dto;
    }

    public StudyType toEntity(StudyTypeDTO dto) {
        StudyType studyType = new StudyType();
        studyType.setId(dto.getId());
        studyType.setName(dto.getName());
        return studyType;
    }
}
