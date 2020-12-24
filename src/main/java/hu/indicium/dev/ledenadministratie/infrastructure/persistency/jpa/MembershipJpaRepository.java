package hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.MembershipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MembershipJpaRepository extends JpaRepository<Membership, UUID> {
    boolean existsByMembershipId(MembershipId membershipId);
}
