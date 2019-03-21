package hu.indicium.dev.lit.group.exceptions;

public class GroupMembershipNotFoundException extends RuntimeException {
    public GroupMembershipNotFoundException() {
        super("Group membership not found!");
    }
}
