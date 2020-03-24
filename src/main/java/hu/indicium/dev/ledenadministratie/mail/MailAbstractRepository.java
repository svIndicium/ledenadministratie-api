package hu.indicium.dev.ledenadministratie.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface MailAbstractRepository<T> extends JpaRepository<T, Long> {
    int countByVerificationToken(String token);

    Optional<T> findByVerificationToken(String token);
}
