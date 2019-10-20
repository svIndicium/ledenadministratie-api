package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<StudyTypeDTO> getAllStudyTypes() {
        List<StudyType> studyTypes = studyTypeRepository.findAll();
        return studyTypes.stream()
                .map(studyTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudyTypeDTO createStudyType(StudyTypeDTO studyTypeDTO) {
        StudyType studyType = studyTypeMapper.toEntity(studyTypeDTO);
        studyType = studyTypeRepository.save(studyType);
        return studyTypeMapper.toDTO(studyType);
    }
}
