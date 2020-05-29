package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;
import hu.indicium.dev.ledenadministratie.group.dto.GroupMemberDto;

import java.util.List;

public interface GroupService {
    List<GroupDTO> getGroups();

    GroupDTO getGroupById(Long groupId);

    GroupDTO updateGroup(GroupDTO group);

    GroupDTO createGroup(GroupDTO group);

    boolean existsByName(String name);

    GroupMemberDto addMemberToGroup(Long groupId, GroupMemberDto groupMember);

    List<GroupMemberDto> getMembersFromGroup(Long groupId);

    List<GroupMemberDto> findByGroupIdAndUserId(Long groupId, Long userId);

    List<GroupMemberDto> findByUserId(String userId);
}
