package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;

import java.util.Collection;

public interface MemberQueryService {
    Member getMemberById(MemberId memberId);

    Collection<Member> getAllMembers();
}
