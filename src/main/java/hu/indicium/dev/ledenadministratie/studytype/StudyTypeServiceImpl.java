package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyTypeServiceImpl implements StudyTypeService {

    private final StudyTypeRepository studyTypeRepository;

    private final StudyTypeMapper studyTypeMapper;

    private final Validator<StudyType> studyTypeValidator;

    public StudyTypeServiceImpl(StudyTypeRepository studyTypeRepository, StudyTypeMapper studyTypeMapper, Validator<StudyType> studyTypeValidator) {
        this.studyTypeRepository = studyTypeRepository;
        this.studyTypeMapper = studyTypeMapper;
        this.studyTypeValidator = studyTypeValidator;
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
    @PreAuthorize("hasPermission('create:studyType')")
    public StudyTypeDTO createStudyType(StudyTypeDTO studyTypeDTO) {
        StudyType studyType = studyTypeMapper.toEntity(studyTypeDTO);
        studyTypeValidator.validate(studyType);
        studyType = studyTypeRepository.save(studyType);
        return studyTypeMapper.toDTO(studyType);
    }

    @Override
    public boolean isNameInUse(String studyTypeName) {
        return studyTypeRepository.existsByName(studyTypeName);
    }
}
