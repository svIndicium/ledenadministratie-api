package hu.indicium.dev.ledenadministratie.group.repositories;

import hu.indicium.dev.ledenadministratie.group.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroupId(Long groupId);

    List<GroupMember> findByGroupIdAndUserId(Long groupId, Long userId);

    GroupMember findByGroupIdAndUserIdAndStartDate(Long groupId, Long userId, Date startDate);

    List<GroupMember> findByUser_Auth0UserId(String auth0UserId);
}
