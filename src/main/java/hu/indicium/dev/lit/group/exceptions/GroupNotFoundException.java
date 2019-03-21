package hu.indicium.dev.lit.group.exceptions;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long groupId) {
        super(String.format("Group with id %d not found", groupId));
    }
}
