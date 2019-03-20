package hu.indicium.dev.lit.membership;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Set<Membership> findAllByUserId(Long userId);
}
