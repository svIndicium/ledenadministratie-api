package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.mail.MailObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findAllByApprovedIsTrueOrApprovedIsFalseAndCommentIsNotNull();

    List<Registration> findAllByApprovedIsFalseAndCommentIsNull();

    int countByVerificationToken(String token);

    Optional<MailObject> findByVerificationToken(String token);

    boolean existsByMailAddressAndVerifiedAtIsNotNull(String mailAddress);
}
