package hu.indicium.dev.lit.study;

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
    public List<Study> getStudiesByUserId(Long userId) {
        return studyRepository.getByUserId(userId);
    }

    @Override
    public Study createStudy(Study study) {
        return this.validateAndSaveStudy(study);
    }

    @Override
    public Study updateStudy(Study study) {
        return this.validateAndSaveStudy(study);
    }

    private Study validateAndSaveStudy(Study study) {
        return studyRepository.save(study);
    }
}
