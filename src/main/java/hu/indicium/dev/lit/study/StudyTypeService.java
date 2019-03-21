package hu.indicium.dev.lit.study;

import hu.indicium.dev.lit.study.exceptions.StudyTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyTypeService implements StudyTypeServiceInterface {

    private final StudyTypeRepository studyTypeRepository;

    @Autowired
    public StudyTypeService(StudyTypeRepository studyTypeRepository) {
        this.studyTypeRepository = studyTypeRepository;
    }

    @Override
    public List<StudyType> getAllStudyTypes() {
        return studyTypeRepository.findAll();
    }

    @Override
    public StudyType getStudyTypeById(Long studyTypeId) {
        return studyTypeRepository.findById(studyTypeId)
                .orElseThrow(StudyTypeNotFoundException::new);
    }

    @Override
    public StudyType createStudyType(StudyType studyType) {
        return validateAndSaveStudyType(studyType);
    }

    @Override
    public StudyType updateStudyType(StudyType newStudyType) {
        StudyType studyType = this.getStudyTypeById(newStudyType.getId());
        studyType.setShortName(newStudyType.getShortName());
        studyType.setLongName(newStudyType.getLongName());
        return validateAndSaveStudyType(studyType);
    }

    @Override
    public void deleteStudyType(Long studyTypeId) {
        studyTypeRepository.deleteById(studyTypeId);
    }

    private StudyType validateAndSaveStudyType(StudyType studyType) {
        return studyTypeRepository.save(studyType);
    }
}
