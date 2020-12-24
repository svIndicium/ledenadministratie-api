package hu.indicium.dev.ledenadministratie.infrastructure.persistency;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberRepository;
import hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa.MemberJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@AllArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member getMemberById(MemberId memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Member %s not found.", memberId.getAuthId())));
    }

    @Override
    public Collection<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
