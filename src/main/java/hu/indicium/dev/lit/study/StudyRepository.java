package hu.indicium.dev.lit.study;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> getByUserId(Long userId);
}
