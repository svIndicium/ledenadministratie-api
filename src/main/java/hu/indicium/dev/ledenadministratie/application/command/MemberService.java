package hu.indicium.dev.ledenadministratie.application.command;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;

public interface MemberService {
    Member registerMember(Member member);
}
