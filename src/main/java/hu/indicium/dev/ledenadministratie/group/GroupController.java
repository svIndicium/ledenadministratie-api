package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;
import hu.indicium.dev.ledenadministratie.group.requests.CreateGroupRequest;
import hu.indicium.dev.ledenadministratie.group.requests.UpdateGroupRequest;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static hu.indicium.dev.ledenadministratie.util.BaseUrl.API_V1;

@RestController
@RequestMapping(API_V1 + "/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groups = groupService.getGroups();
        return ResponseBuilder.ok()
                .data(groups)
                .build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<GroupDTO> createGroup(@RequestBody @Valid CreateGroupRequest createGroupRequest) {
        GroupDTO groupDTO = new GroupDTO(createGroupRequest.getName(), createGroupRequest.getDescription());
        GroupDTO group = groupService.createGroup(groupDTO);
        return ResponseBuilder.created()
                .data(group)
                .build();
    }

    @PutMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<GroupDTO> updateGroup(@RequestBody @Valid UpdateGroupRequest updateGroupRequest, @PathVariable Long groupId) {
        GroupDTO groupDTO = new GroupDTO(updateGroupRequest.getName(), updateGroupRequest.getDescription());
        groupDTO.setId(groupId);
        GroupDTO group = groupService.updateGroup(groupDTO);
        return ResponseBuilder.accepted()
                .data(group)
                .build();
    }
}
