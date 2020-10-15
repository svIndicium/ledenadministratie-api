package hu.indicium.dev.ledenadministratie.domain.model.user.member;

import java.util.Collection;

public interface MemberRepository {
    MemberId nextIdentity();

    Member save(Member member);

    Member getMemberById(MemberId memberId);

    Collection<Member> getAllMembers();
}
