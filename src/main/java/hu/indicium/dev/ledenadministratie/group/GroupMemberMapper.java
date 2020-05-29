package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.dto.GroupMemberDto;
import hu.indicium.dev.ledenadministratie.user.User;

public class GroupMemberMapper {
    public static GroupMember map(GroupMemberDto groupMemberDto) {
        GroupMember groupMember = new GroupMember();
        groupMember.setId(groupMemberDto.getId());
        groupMember.setStartDate(groupMemberDto.getStartDate());
        groupMember.setEndDate(groupMemberDto.getEndDate());
        User user = new User();
        user.setAuth0UserId(groupMemberDto.getUserId());
        groupMember.setUser(user);
        return groupMember;
    }

    public static GroupMemberDto map(GroupMember groupMember) {
        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setId(groupMember.getId());
        groupMemberDto.setFullName(groupMember.getUser().getFirstName() + " " + groupMember.getUser().getFullLastName());
        groupMemberDto.setUserId(groupMember.getUser().getAuth0UserId());
        groupMemberDto.setStartDate(groupMember.getStartDate());
        groupMemberDto.setEndDate(groupMember.getEndDate());
        groupMemberDto.setCreatedAt(groupMember.getCreatedAt());
        groupMemberDto.setUpdatedAt(groupMember.getUpdatedAt());
        return groupMemberDto;
    }
}
