package hu.indicium.dev.lit.group.exceptions;

public class GroupNameAlreadyInUseException extends RuntimeException {
    public GroupNameAlreadyInUseException(String groupName) {
        super(String.format("%s is already in use!", groupName));
    }
}
