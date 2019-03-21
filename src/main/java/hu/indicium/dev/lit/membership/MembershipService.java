package hu.indicium.dev.lit.membership;

import hu.indicium.dev.lit.membership.exceptions.MembershipStartDateAfterEndDateException;
import hu.indicium.dev.lit.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MembershipService implements MembershipServiceInterface {

    private final MembershipRepository membershipRepository;

    private final UserServiceInterface userService;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository, UserServiceInterface userService) {
        this.membershipRepository = membershipRepository;
        this.userService = userService;
    }

    @Override
    public Membership createMembership(Membership membership, Long userId) {
        membership.setUser(userService.getUserById(userId));
        if (membership.getStartDate().after(membership.getEndDate())) {
            throw new MembershipStartDateAfterEndDateException();
        }
        return membershipRepository.save(membership);
    }

    @Override
    public Set<Membership> getMembershipsByUserId(Long userId) {
        return membershipRepository.findAllByUserId(userId);
    }
}
