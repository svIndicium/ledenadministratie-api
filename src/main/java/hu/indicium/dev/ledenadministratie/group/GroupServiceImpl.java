package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.domain.Group;
import hu.indicium.dev.ledenadministratie.group.domain.GroupMember;
import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;
import hu.indicium.dev.ledenadministratie.group.dto.GroupMemberDto;
import hu.indicium.dev.ledenadministratie.group.mapper.GroupMapper;
import hu.indicium.dev.ledenadministratie.group.mapper.GroupMemberMapper;
import hu.indicium.dev.ledenadministratie.group.repositories.GroupMemberRepository;
import hu.indicium.dev.ledenadministratie.group.repositories.GroupRepository;
import hu.indicium.dev.ledenadministratie.user.UserService;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final GroupMemberRepository groupMemberRepository;

    private final Validator<Group> groupValidator;

    private final Validator<GroupMember> groupMemberValidator;

    private final UserService userService;

    public GroupServiceImpl(GroupRepository groupRepository, Validator<Group> groupValidator, GroupMemberRepository groupMemberRepository, UserService userService, Validator<GroupMember> groupMemberValidator) {
        this.groupRepository = groupRepository;
        this.groupValidator = groupValidator;
        this.groupMemberRepository = groupMemberRepository;
        this.userService = userService;
        this.groupMemberValidator = groupMemberValidator;
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

    @Override
    public GroupMemberDto addMemberToGroup(Long groupId, GroupMemberDto groupMemberDto) {
        Group group = getGroup(groupId);
        GroupMember groupMember = GroupMemberMapper.map(groupMemberDto);
        UserDTO user = userService.getUserByAuthId(groupMemberDto.getUserId());
        groupMember.getUser().setId(user.getId());
        groupMember.setGroup(group);
        groupMemberValidator.validate(groupMember);
        group.addMember(groupMember);
        saveAndValidate(group);
        return GroupMemberMapper.map(groupMemberRepository.findByGroupIdAndUserIdAndStartDate(groupId, user.getId(), groupMember.getStartDate()));
    }

    @Override
    public List<GroupMemberDto> getMembersFromGroup(Long groupId) {
        List<GroupMember> groupMembers = groupMemberRepository.findByGroupId(groupId);
        return groupMembers.stream()
                .map(GroupMemberMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupMemberDto> findByGroupIdAndUserId(Long groupId, Long userId) {
        List<GroupMember> groupMembers = groupMemberRepository.findByGroupIdAndUserId(groupId, userId);
        return groupMembers.stream()
                .map(GroupMemberMapper::map)
                .collect(Collectors.toList());
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
