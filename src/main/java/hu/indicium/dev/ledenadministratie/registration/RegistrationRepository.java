package hu.indicium.dev.ledenadministratie.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findAllByApprovedIsFalseAndCommentIsNull();
}
