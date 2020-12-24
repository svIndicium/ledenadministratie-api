package hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, String> {
    Optional<Member> findByMemberId(MemberId memberId);
}
