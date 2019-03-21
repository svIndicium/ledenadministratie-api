package hu.indicium.dev.lit.group;

import java.util.List;

public interface GroupServiceInterface {
    Group createNewGroup(Group group);

    Group updateGroup(Group group);

    void deleteGroup(Long groupId);

    List<Group> getAllGroups();
}
