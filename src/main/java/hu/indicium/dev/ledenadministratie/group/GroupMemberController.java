package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.dto.GroupMemberDto;
import hu.indicium.dev.ledenadministratie.group.requests.AddGroupMemberRequest;
import hu.indicium.dev.ledenadministratie.util.BaseUrl;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseUrl.API_V1)
public class GroupMemberController {

    private final GroupService groupService;

    public GroupMemberController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups/{groupId}/members")
    @ResponseStatus(HttpStatus.OK)
    public Response<List<GroupMemberDto>> getMembersOfGroup(@PathVariable Long groupId) {
        List<GroupMemberDto> groupMemberDtos = groupService.getMembersFromGroup(groupId);
        return ResponseBuilder.ok()
                .data(groupMemberDtos)
                .build();
    }

    @PostMapping("/groups/{groupId}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<GroupMemberDto> addMemberToGroup(@PathVariable Long groupId, @RequestBody AddGroupMemberRequest addGroupMemberRequest) {
        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setUserId(addGroupMemberRequest.getUserId());
        groupMemberDto.setStartDate(addGroupMemberRequest.getStartDate());
        groupMemberDto.setEndDate(addGroupMemberRequest.getEndDate());
        groupMemberDto = this.groupService.addMemberToGroup(groupId, groupMemberDto);
        return ResponseBuilder.created()
                .data(groupMemberDto)
                .build();
    }
}
