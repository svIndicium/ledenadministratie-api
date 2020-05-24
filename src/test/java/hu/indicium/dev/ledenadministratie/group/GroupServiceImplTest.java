package hu.indicium.dev.ledenadministratie.group;

import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GroupServiceImpl.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Group Service")
class GroupServiceImplTest {

    @MockBean
    private GroupRepository groupRepository;

    @MockBean
    private Validator<Group> groupValidator;

    @Autowired
    private GroupService groupService;

    private Group group;

    private GroupDTO groupDTO;

    @BeforeEach
    void setUp() {
        group = new Group();
        group.setId(1L);
        group.setName("Bestuur");
        group.setDescription("Het bestuur van de studievereniging");
        group.setCreatedAt(new Date());
        group.setUpdatedAt(new Date());

        groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setDescription(group.getDescription());
        groupDTO.setCreatedAt(group.getCreatedAt());
        groupDTO.setUpdatedAt(group.getUpdatedAt());
    }

    @Test
    @DisplayName("Get list of groups")
    void getListOfGroups() {
        when(groupRepository.findAll()).thenReturn(Collections.singletonList(group));

        List<GroupDTO> groupDTOS = groupService.getGroups();

        assertThat(groupDTOS).hasSize(1);
        GroupDTO groupDTO = groupDTOS.get(0);
        assertThat(groupDTO).isEqualToComparingFieldByField(this.groupDTO);
    }

    @Test
    @DisplayName("Get group by id")
    void getGroupById() {
        when(groupRepository.findById(eq(1L))).thenReturn(Optional.of(group));

        GroupDTO groupDTO = groupService.getGroupById(1L);

        assertThat(groupDTO).isEqualToComparingFieldByField(this.groupDTO);
    }

    @Test
    @DisplayName("Get non-existent group throws exception")
    void shouldThrowEntityNotFoundException_whenGetNonExistentGroup() {
        when(groupRepository.findById(eq(1L))).thenReturn(Optional.empty());

        try {
            GroupDTO groupDTO = groupService.getGroupById(1L);
            fail("Should throw exception because it does not exist");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(EntityNotFoundException.class);
            assertThat(e.getMessage()).contains("1");
        }
    }

    @Test
    @DisplayName("Update group")
    void updateGroup() {
        ArgumentCaptor<Group> groupArgumentCaptor = ArgumentCaptor.forClass(Group.class);

        when(groupRepository.findById(eq(1L))).thenReturn(Optional.of(group));
        when(groupRepository.save(groupArgumentCaptor.capture())).thenReturn(group);

        GroupDTO newGroupDTO = new GroupDTO("Actiecom", "ja dat ja");
        newGroupDTO.setId(1L);

        groupService.updateGroup(newGroupDTO);

        verify(groupValidator, atLeastOnce()).validate(any(Group.class));

        Group group = groupArgumentCaptor.getValue();

        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo(newGroupDTO.getName());
        assertThat(group.getDescription()).isEqualTo(newGroupDTO.getDescription());
        assertThat(group.getId()).isEqualTo(this.group.getId());
        assertThat(group.getCreatedAt()).isEqualTo(this.group.getCreatedAt());
        assertThat(group.getUpdatedAt()).isEqualTo(this.group.getUpdatedAt());
    }

    @Test
    @DisplayName("Create group")
    void createGroup() {
        ArgumentCaptor<Group> groupArgumentCaptor = ArgumentCaptor.forClass(Group.class);

        when(groupRepository.save(groupArgumentCaptor.capture())).thenReturn(group);

        GroupDTO newGroupDTO = new GroupDTO("Actiecom", "ja dat ja");
        newGroupDTO.setId(1L);

        GroupDTO groupDTO = groupService.createGroup(newGroupDTO);

        verify(groupValidator, atLeastOnce()).validate(any(Group.class));

        Group group = groupArgumentCaptor.getValue();

        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo(newGroupDTO.getName());
        assertThat(group.getDescription()).isEqualTo(newGroupDTO.getDescription());
        assertThat(group.getId()).isNull();
        assertThat(group.getCreatedAt()).isEqualTo(this.group.getCreatedAt());
        assertThat(group.getUpdatedAt()).isEqualTo(this.group.getUpdatedAt());
    }

    @Test
    @DisplayName("Update non-existent group throws exception")
    void shouldThrowEntityNotFoundException_whenUpdateNonExistentGroup() {
        when(groupRepository.findById(eq(1L))).thenReturn(Optional.empty());

        GroupDTO newGroupDTO = new GroupDTO("Actiecom", "ja dat ja");
        newGroupDTO.setId(1L);

        try {
            GroupDTO groupDTO = groupService.updateGroup(newGroupDTO);
            fail("Should throw exception because it does not exist");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(EntityNotFoundException.class);
            assertThat(e.getMessage()).contains("1");
        }

        verify(groupValidator, never()).validate(any(Group.class));
        verify(groupRepository, never()).save(any(Group.class));
    }

    @TestConfiguration
    static class GroupServiceTestContextConfiguration {
        @Autowired
        private GroupRepository groupRepository;

        @Autowired
        private Validator<Group> groupValidator;

        @Bean
        public GroupService groupService() {
            return new GroupServiceImpl(groupRepository, groupValidator);
        }
    }
}