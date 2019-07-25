package hu.indicium.dev.lit.group;

import hu.indicium.dev.lit.group.dto.*;
import hu.indicium.dev.lit.response.DeleteSuccessResponse;
import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class GroupController {
    private final GroupServiceInterface groupService;

    private final ModelMapper modelMapper;

    @Autowired
    public GroupController(GroupServiceInterface groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/groups")
    @PreAuthorize("hasPermission('read:groups')")
    public Response getAllGroups() {
        return new SuccessResponse(
                groupService.getAllGroups()
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/groups")
    @PreAuthorize("hasPermission('create:groups')")
    public Response createNewGroup(@RequestBody NewGroupDTO newGroupDTO) {
        return new SuccessResponse(
                convertToDTO(groupService.createNewGroup(convertToEntity(newGroupDTO)))
        );
    }

    @PutMapping("/groups/{groupId}")
    @PreAuthorize("hasPermission('edit:groups')")
    public Response updateGroup(@RequestBody GroupDTO groupDTO, @PathVariable Long groupId) {
        Group group = convertToEntity(groupDTO);
        group.setId(groupId);
        return new SuccessResponse(convertToDTO(groupService.updateGroup(group)));
    }

    @DeleteMapping("/groups/{groupId}")
    @PreAuthorize("hasPermission('delete:groups')")
    public Response deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return new DeleteSuccessResponse();
    }

    @GetMapping("/groups/{groupId}/members")
    @PreAuthorize("hasPermission('read:group_members')")
    public Response getGroupMembers(@PathVariable Long groupId) {
        return new SuccessResponse(groupService.getAllMembersByGroupId(groupId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/groups/{groupId}/members/{userId}")
    @PreAuthorize("hasPermission('add:group_members')")
    public Response addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId, @RequestBody NewGroupMembershipDTO groupMembershipDTO) {
        return new SuccessResponse(convertToDTO(groupService.addUserToGroup(userId, groupId, groupMembershipDTO)));
    }

    @DeleteMapping("/groupmemberships/{groupMembershipId}")
    @PreAuthorize("hasPermission('delete:group_members')")
    public Response removeUserFromGroup(@PathVariable Long groupMembershipId) {
        groupService.removeUserFromGroup(groupMembershipId);
        return new DeleteSuccessResponse();
    }

    @PutMapping("/groupmemberships/{groupMembershipId}")
    @PreAuthorize("hasPermission('edit:group_members')")
    public Response editGroupMembership(@PathVariable Long groupMembershipId, @RequestBody UpdateGroupMembershipDTO groupMembershipDTO) {
        GroupMembership groupMembership = convertToEntity(groupMembershipDTO);
        groupMembership.setId(groupMembershipId);
        return new SuccessResponse(convertToDTO(groupService.updateGroupMembership(groupMembership)));
    }

    private GroupDTO convertToDTO(Group group) {
        return modelMapper.map(group, GroupDTO.class);
    }

    private GroupMembershipDTO convertToDTO(GroupMembership groupMembership) {
        return modelMapper.map(groupMembership, GroupMembershipDTO.class);
    }

    private Group convertToEntity(NewGroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

    private Group convertToEntity(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

    private GroupMembership convertToEntity(UpdateGroupMembershipDTO groupMembershipDTO) {
        return modelMapper.map(groupMembershipDTO, GroupMembership.class);
    }
}
