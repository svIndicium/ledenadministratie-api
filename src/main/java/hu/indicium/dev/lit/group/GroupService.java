package hu.indicium.dev.lit.group;

import hu.indicium.dev.lit.group.exceptions.GroupNameAlreadyInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService implements GroupServiceInterface {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
