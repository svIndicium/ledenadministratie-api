package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.validation.UniqueGroupNameValidator;
import hu.indicium.dev.ledenadministratie.util.ValidatorGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class GroupValidationConfiguration {

    private final GroupRepository groupRepository;

    public GroupValidationConfiguration(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Bean
    ValidatorGroup<Group> groupValidatorGroup() {
        return new ValidatorGroup<>(Arrays.asList(new UniqueGroupNameValidator(groupRepository)));
    }
}
