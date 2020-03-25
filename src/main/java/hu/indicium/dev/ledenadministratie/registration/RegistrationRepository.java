package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.mail.MailAbstractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends MailAbstractRepository<Registration>, JpaRepository<Registration, Long> {
    List<Registration> findAllByApprovedIsTrueOrApprovedIsFalseAndCommentIsNotNull();

    List<Registration> findAllByApprovedIsFalseAndCommentIsNull();
}
