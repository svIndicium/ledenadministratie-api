package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;

import java.util.Collection;

public interface MembershipQueryService {
    Collection<Membership> getMembershipsByMemberId(MemberId memberId);
}
