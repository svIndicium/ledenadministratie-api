package hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudyTypeJpaRepository extends JpaRepository<StudyType, UUID> {
    boolean existsByStudyTypeId(StudyTypeId studyTypeId);

    Optional<StudyType> findByStudyTypeId(StudyTypeId studyTypeId);
}
