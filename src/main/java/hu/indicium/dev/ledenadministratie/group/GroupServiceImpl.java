package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
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
    public GroupDTO getGroupById(Long groupId) {
        Group group = getGroup(groupId);
        return GroupMapper.map(group);
    }

    @Override
    public GroupDTO updateGroup(GroupDTO groupDTO) {
        Group group = getGroup(groupDTO.getId());
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        group = groupRepository.save(group);
        return GroupMapper.map(group);
    }

    private Group getGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Groep %d niet gevonden", groupId)));
    }
}
