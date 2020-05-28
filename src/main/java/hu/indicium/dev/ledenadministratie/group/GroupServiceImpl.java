package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final Validator<Group> groupValidator;

    public GroupServiceImpl(GroupRepository groupRepository, Validator<Group> groupValidator) {
        this.groupRepository = groupRepository;
        this.groupValidator = groupValidator;
    }

    @Override
    @PreAuthorize("hasPermission('read:group')")
    public List<GroupDTO> getGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOS = new ArrayList<>();
        for (Group group : groups) {
            GroupDTO groupDTO = GroupMapper.map(group);
            groupDTOS.add(groupDTO);
        }
        return groupDTOS;
    }

    @Override
    @PreAuthorize("hasPermission('read:group')")
    public GroupDTO getGroupById(Long groupId) {
        Group group = getGroup(groupId);
        return GroupMapper.map(group);
    }

    @Override
    @PreAuthorize("hasPermission('write:group')")
    public GroupDTO updateGroup(GroupDTO groupDTO) {
        Group group = getGroup(groupDTO.getId());
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        group = this.saveAndValidate(group);
        return GroupMapper.map(group);
    }

    @Override
    @PreAuthorize("hasPermission('write:group')")
    public GroupDTO createGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        if (this.existsByName(group.getName())) {
            throw new EntityExistsException(String.format("Groep met de naam %s bestaat al", group.getName()));
        }
        group = this.saveAndValidate(group);
        return GroupMapper.map(group);
    }

    @Override
    public boolean existsByName(String name) {
        return groupRepository.existsByNameIgnoreCase(name);
    }

    private Group getGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Groep %d niet gevonden", groupId)));
    }

    private Group saveAndValidate(Group group) {
        groupValidator.validate(group);
        return groupRepository.save(group);
    }
}
