package hu.indicium.dev.lit.group;

import hu.indicium.dev.lit.group.dto.NewGroupMembershipDTO;
import hu.indicium.dev.lit.group.exceptions.GroupNameAlreadyInUseException;
import hu.indicium.dev.lit.group.exceptions.GroupNotFoundException;
import hu.indicium.dev.lit.user.User;
import hu.indicium.dev.lit.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService implements GroupServiceInterface {

    private final GroupRepository groupRepository;

    private final UserServiceInterface userService;

    private final GroupMembershipRepository groupMembershipRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserServiceInterface userService, GroupMembershipRepository groupMembershipRepository) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.groupMembershipRepository = groupMembershipRepository;
    }

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    @Override
    public Group createNewGroup(Group group) {
        return saveAndValidateGroup(group);
    }

    @Override
    public Group updateGroup(Group group) {
        return saveAndValidateGroup(group);
    }

    @Override
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public GroupMembership addUserToGroup(Long userId, Long groupId, NewGroupMembershipDTO groupMembershipDTO) {
        User user = userService.getUserById(userId);
        Group group = this.getGroupById(groupId);
        GroupMembership groupMembership = group.addMembership(user, groupMembershipDTO.getStartDate(), groupMembershipDTO.getEndDate());
        return groupMembershipRepository.save(groupMembership);
    }

    @Override
    public void removeUserFromGroup(Long groupMembershipId) {
        groupMembershipRepository.deleteById(groupMembershipId);
    }

    private Group saveAndValidateGroup(Group group) {
        if (groupRepository.existsByName(group.getName())) {
            Group nameGroup = groupRepository.findByName(group.getName());
            if (!nameGroup.getId().equals(group.getId())) {
                throw new GroupNameAlreadyInUseException(group.getName());
            }
        }
        return groupRepository.save(group);
    }
}
