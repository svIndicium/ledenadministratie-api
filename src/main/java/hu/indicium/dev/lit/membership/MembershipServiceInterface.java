package hu.indicium.dev.lit.membership;

import hu.indicium.dev.lit.user.User;

import java.util.Date;
import java.util.Set;

public interface MembershipServiceInterface {
    Membership createMembership(Date startDate, Date endDate, User user);

    Set<Membership> getMembershipsByUserId(Long userId);
}
