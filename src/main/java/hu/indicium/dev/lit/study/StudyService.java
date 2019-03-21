package hu.indicium.dev.lit.study;

import hu.indicium.dev.lit.study.exceptions.StudyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyService implements StudyServiceInterface {

    private final StudyRepository studyRepository;

    @Autowired
    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @Override
    public Study getStudyById(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(StudyNotFoundException::new);
    }

    @Override
    public List<Study> getStudiesByUserId(Long userId) {
        return studyRepository.getByUserId(userId);
    }

    @Override
    public Study createStudy(Study study) {
        return this.validateAndSaveStudy(study);
    }

    @Override
    public Study updateStudy(Study newStudy) {
        Study study = this.getStudyById(newStudy.getId());
        study.setType(newStudy.getType());
        study.setStartDate(newStudy.getStartDate());
        return this.validateAndSaveStudy(study);
    }

    private Study validateAndSaveStudy(Study study) {
        return studyRepository.save(study);
    }
}
