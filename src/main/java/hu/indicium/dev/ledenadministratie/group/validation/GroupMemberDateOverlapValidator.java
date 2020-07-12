package hu.indicium.dev.ledenadministratie.group.validation;

import hu.indicium.dev.ledenadministratie.group.GroupService;
import hu.indicium.dev.ledenadministratie.group.domain.GroupMember;
import hu.indicium.dev.ledenadministratie.group.dto.GroupMemberDto;
import hu.indicium.dev.ledenadministratie.util.Validator;

import java.util.List;

public class GroupMemberDateOverlapValidator implements Validator<GroupMember> {

    private final GroupService groupService;

    public GroupMemberDateOverlapValidator(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void validate(GroupMember groupMember) {
        List<GroupMemberDto> allUserMemberships = groupService.findByGroupIdAndUserId(groupMember.getGroup().getId(), groupMember.getUser().getId());
        if (allUserMemberships.size() != 0) {
            for (GroupMemberDto membership : allUserMemberships) {
                if ((membership.getEndDate().after(groupMember.getStartDate()) &&
                        membership.getStartDate().before(groupMember.getEndDate())) ||
                        membership.getStartDate().getTime() == groupMember.getStartDate().getTime() ||
                        membership.getEndDate().getTime() == groupMember.getEndDate().getTime()
                ) {
                    throw new RuntimeException("Lid is al commissielid tussen de start en de eind datum.");
                }
            }
        }
    }
}
