package hu.indicium.dev.ledenadministratie.group.validation;

import hu.indicium.dev.ledenadministratie.group.Group;
import hu.indicium.dev.ledenadministratie.group.GroupRepository;
import hu.indicium.dev.ledenadministratie.util.Validator;

import javax.persistence.EntityExistsException;

public class UniqueGroupNameValidator implements Validator<Group> {

    private final GroupRepository groupRepository;

    public UniqueGroupNameValidator(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void validate(Group group) {
        if (groupRepository.existsByNameIgnoreCase(group.getName())) {
            throw new EntityExistsException(String.format("Groep met de naam %s bestaat al", group.getName()));
        }
    }
}
