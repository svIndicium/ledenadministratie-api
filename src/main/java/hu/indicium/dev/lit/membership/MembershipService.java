package hu.indicium.dev.lit.membership;

import hu.indicium.dev.lit.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class MembershipService implements MembershipServiceInterface {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Membership createMembership(Date startDate, Date endDate, User user) {
        Membership membership = new Membership(startDate, endDate, user);
        return membershipRepository.save(membership);
    }

    @Override
    public Set<Membership> getMembershipsByUserId(Long userId) {
        return membershipRepository.findAllByUserId(userId);
    }
}
