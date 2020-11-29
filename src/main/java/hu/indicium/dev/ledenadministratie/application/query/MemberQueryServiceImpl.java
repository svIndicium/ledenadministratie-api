package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    @PreAuthorize("hasPermission('admin:member') || (hasPermission('read:member') && #memberId.authId == principal)")
    public Member getMemberById(MemberId memberId) {
        return memberRepository.getMemberById(memberId);
    }

    @Override
    @PreAuthorize("hasPermission('admin:member')")
    public Collection<Member> getAllMembers() {
        return memberRepository.getAllMembers();
    }
}
