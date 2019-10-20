package hu.indicium.dev.ledenadministratie.studytype;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyTypeRepository extends JpaRepository<StudyType, Long> {
    boolean existsByName(String name);
}
