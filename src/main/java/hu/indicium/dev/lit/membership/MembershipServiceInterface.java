package hu.indicium.dev.lit.membership;

import java.util.Set;

public interface MembershipServiceInterface {
    Membership createMembership(Membership membership, Long userId);

    Set<Membership> getMembershipsByUserId(Long userId);
}
