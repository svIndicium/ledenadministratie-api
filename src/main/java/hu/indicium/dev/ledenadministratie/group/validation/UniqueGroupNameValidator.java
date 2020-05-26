package hu.indicium.dev.ledenadministratie.group.validation;

import hu.indicium.dev.ledenadministratie.group.Group;
import hu.indicium.dev.ledenadministratie.group.GroupService;
import hu.indicium.dev.ledenadministratie.util.Validator;

import javax.persistence.EntityExistsException;

public class UniqueGroupNameValidator implements Validator<Group> {

    private GroupService groupService;

    public UniqueGroupNameValidator(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void validate(Group group) {
        if (groupService.existsByName(group.getName())) {
            throw new EntityExistsException(String.format("Groep met de naam %s bestaat al", group.getName()));
        }
    }
}
