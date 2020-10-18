package hu.indicium.dev.ledenadministratie.infrastructure.persistency;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeRepository;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa.StudyTypeJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class StudyTypeRepositoryImpl implements StudyTypeRepository {

    private final StudyTypeJpaRepository studyTypeRepository;

    @Override
    public StudyTypeId nextIdentity() {
        UUID uuid = UUID.randomUUID();
        StudyTypeId studyTypeId = StudyTypeId.fromId(uuid);
        if (studyTypeRepository.existsByStudyTypeId(studyTypeId)) {
            return nextIdentity();
        }
        return studyTypeId;
    }

    @Override
    public StudyType getStudyTypeById(StudyTypeId studyTypeId) {
        return studyTypeRepository.findById(studyTypeId.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Studytype %s not found.", studyTypeId.getId().toString())));
    }

    @Override
    public StudyType save(StudyType studyType) {
        return studyTypeRepository.save(studyType);
    }

    @Override
    public Collection<StudyType> getAllStudyTypes() {
        return studyTypeRepository.findAll();
    }
}
