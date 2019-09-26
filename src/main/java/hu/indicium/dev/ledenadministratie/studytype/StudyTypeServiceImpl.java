package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class StudyTypeServiceImpl implements StudyTypeService {

    private final StudyTypeRepository studyTypeRepository;

    public StudyTypeServiceImpl(StudyTypeRepository studyTypeRepository) {
        this.studyTypeRepository = studyTypeRepository;
    }

    @Override
    public StudyTypeDTO getStudyTypeById(Long studyTypeId) {
        StudyType studyType = studyTypeRepository.findById(studyTypeId).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        return StudyTypeMapper.toDTO(studyType);
    }
}
