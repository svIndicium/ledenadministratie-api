package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class StudyTypeQueryServiceImpl implements StudyTypeQueryService {

    private final StudyTypeRepository studyTypeRepository;

    @Override
    public StudyType getStudyTypeById(StudyTypeId studyTypeId) {
        return studyTypeRepository.getStudyTypeById(studyTypeId);
    }

    @Override
    public Collection<StudyType> getStudyTypes() {
        return studyTypeRepository.getAllStudyTypes();
    }
}
