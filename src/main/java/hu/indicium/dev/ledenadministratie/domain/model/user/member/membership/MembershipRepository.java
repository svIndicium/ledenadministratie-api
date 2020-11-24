package hu.indicium.dev.ledenadministratie.domain.model.user.member.membership;

public interface MembershipRepository {
    MembershipId nextIdentity();
}
