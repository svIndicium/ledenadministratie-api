package hu.indicium.dev.ledenadministratie.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroupId(Long groupId);

    List<GroupMember> findByGroupIdAndUserId(Long groupId, Long userId);

    GroupMember findByGroupIdAndUserIdAndStartDate(Long groupId, Long userId, Date startDate);
}
