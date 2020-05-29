package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.validation.DateOverlapValidator;
import hu.indicium.dev.ledenadministratie.util.Validator;
import hu.indicium.dev.ledenadministratie.util.ValidatorGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Arrays;

@Configuration
public class GroupMemberValidationConfiguration {

    private final GroupService groupService;

    public GroupMemberValidationConfiguration(@Lazy GroupService groupService) {
        this.groupService = groupService;
    }

    @Bean
    Validator<GroupMember> groupMemberValidator() {
        return new ValidatorGroup<>(Arrays.asList(new DateOverlapValidator(groupService)));
    }
}
