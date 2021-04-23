package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.MembershipRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class MembershipQueryServiceImpl implements MembershipQueryService {

    private final MembershipRepository membershipRepository;

    @Override
    @PreAuthorize("hasPermission('manage-members') || (hasPermission('view-members') && userIdEquals(#memberId.authId))")
    public Collection<Membership> getMembershipsByMemberId(MemberId memberId) {
        return membershipRepository.getMembershipsByMemberId(memberId);
    }
}
