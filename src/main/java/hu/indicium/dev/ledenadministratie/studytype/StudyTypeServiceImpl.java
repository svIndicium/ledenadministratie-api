package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class StudyTypeServiceImpl implements StudyTypeService {

    private final StudyTypeRepository studyTypeRepository;

    private final StudyTypeMapper studyTypeMapper;

    public StudyTypeServiceImpl(StudyTypeRepository studyTypeRepository, StudyTypeMapper studyTypeMapper) {
        this.studyTypeRepository = studyTypeRepository;
        this.studyTypeMapper = studyTypeMapper;
    }

    @Override
    public StudyTypeDTO getStudyTypeById(Long studyTypeId) {
        StudyType studyType = studyTypeRepository.findById(studyTypeId).orElseThrow(() -> new EntityNotFoundException("Studytype " + studyTypeId + " not found"));
        return studyTypeMapper.toDTO(studyType);
    }
}
