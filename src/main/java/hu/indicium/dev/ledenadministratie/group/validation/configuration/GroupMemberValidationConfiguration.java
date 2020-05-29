package hu.indicium.dev.ledenadministratie.group.validation.configuration;

import hu.indicium.dev.ledenadministratie.group.GroupService;
import hu.indicium.dev.ledenadministratie.group.domain.GroupMember;
import hu.indicium.dev.ledenadministratie.group.validation.GroupMemberDateOverlapValidator;
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
        return new ValidatorGroup<>(Arrays.asList(new GroupMemberDateOverlapValidator(groupService)));
    }
}
