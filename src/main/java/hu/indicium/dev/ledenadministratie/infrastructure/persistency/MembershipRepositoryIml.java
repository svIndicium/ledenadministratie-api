package hu.indicium.dev.ledenadministratie.infrastructure.persistency;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.MembershipId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.MembershipRepository;
import hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa.MembershipJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class MembershipRepositoryIml implements MembershipRepository {

    private final MembershipJpaRepository membershipJpaRepository;

    @Override
    public MembershipId nextIdentity() {
        UUID uuid = UUID.randomUUID();
        MembershipId membershipId = MembershipId.fromId(uuid);
        if (membershipJpaRepository.existsByMembershipId(membershipId)) {
            return nextIdentity();
        }
        return membershipId;
    }

    @Override
    public Collection<Membership> getMembershipsByMemberId(MemberId memberId) {
        return membershipJpaRepository.getMembershipsByMemberMemberId(memberId);
    }

    @Override
    public Membership getMembershipByPaymentId(PaymentId paymentId) {
        return membershipJpaRepository.getMembershipByPaymentId(paymentId);
    }

    @Override
    public Membership save(Membership membership) {
        return membershipJpaRepository.save(membership);
    }
}
