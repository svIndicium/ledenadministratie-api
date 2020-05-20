package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;

import java.util.List;

public interface GroupService {
    List<GroupDTO> getGroups();

    GroupDTO getGroupById(Long groupId);

    GroupDTO updateGroup(GroupDTO group);

    GroupDTO createGroup(GroupDTO group);
}
