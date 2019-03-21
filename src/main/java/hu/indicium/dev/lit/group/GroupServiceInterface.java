package hu.indicium.dev.lit.group;

import hu.indicium.dev.lit.group.dto.NewGroupMembershipDTO;

import java.util.List;

public interface GroupServiceInterface {
    Group getGroupById(Long groupId);

    Group createNewGroup(Group group);

    Group updateGroup(Group group);

    void deleteGroup(Long groupId);

    List<Group> getAllGroups();

    List<GroupMembership> getAllMembersByGroupId(Long groupId);

    GroupMembership addUserToGroup(Long userId, Long groupId, NewGroupMembershipDTO groupMembershipDTO);

    void removeUserFromGroup(Long groupMembershipId);
}
