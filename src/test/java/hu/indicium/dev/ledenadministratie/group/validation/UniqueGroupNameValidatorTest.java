package hu.indicium.dev.ledenadministratie.group.validation;

import hu.indicium.dev.ledenadministratie.group.Group;
import hu.indicium.dev.ledenadministratie.group.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityExistsException;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Unique group name validation")
class UniqueGroupNameValidatorTest {

    @MockBean
    private GroupService groupService;

    @Autowired
    private UniqueGroupNameValidator uniqueGroupNameValidator;

    private Group group;

    @BeforeEach
    void setUp() {
        this.group = new Group();
        group.setId(1L);
        group.setName("Bestuur");
        group.setDescription("Gast ik ben bestuur");
        group.setUpdatedAt(new Date());
        group.setCreatedAt(new Date());
    }

    @Test
    @DisplayName("Pass when no group exists with the same name")
    void shouldPass_whenNoGroupWithTheSameNameExists() {
        when(groupService.existsByName(eq("Bestuur"))).thenReturn(false);

        uniqueGroupNameValidator.validate(group);

        verify(groupService, atLeastOnce()).existsByName(any(String.class));
    }

    @Test
    @DisplayName("Throw exception when group exists with the same name")
    void shouldThrowException_whenGroupWithTheSameNameExists() {

        when(groupService.existsByName(eq("Bestuur"))).thenReturn(true);
        try {
            uniqueGroupNameValidator.validate(group);
            fail("Should throw exception because the name is in use.");
        } catch (Exception e) {
            assertThat(e.getMessage()).contains(group.getName());
            assertThat(e).isInstanceOf(EntityExistsException.class);
        }
    }

    @TestConfiguration
    static class UniqueGroupNameValidatorTestContextConfiguration {
        @Autowired
        private GroupService groupService;

        @Bean
        public UniqueGroupNameValidator uniqueGroupNameValidator() {
            return new UniqueGroupNameValidator(groupService);
        }
    }

}