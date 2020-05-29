package hu.indicium.dev.ledenadministratie.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.group.controller.GroupController;
import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;
import hu.indicium.dev.ledenadministratie.group.requests.CreateGroupRequest;
import hu.indicium.dev.ledenadministratie.group.requests.UpdateGroupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GroupController.class)
@DisplayName("Group controller")
@Tag("Controller")
class GroupControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private GroupService groupService;

    @Autowired
    private MockMvc mvc;

    private GroupDTO groupDTO;

    @BeforeEach
    void setUp() {
        groupDTO = new GroupDTO();
        groupDTO.setId(1L);
        groupDTO.setName("Bestuur");
        groupDTO.setDescription("Het bestuur van de studievereniging");
        groupDTO.setCreatedAt(new Date());
        groupDTO.setUpdatedAt(new Date());
    }

    @Test
    @DisplayName("Create group")
    void createGroup() throws Exception {
        ArgumentCaptor<GroupDTO> groupDTOArgumentCaptor = ArgumentCaptor.forClass(GroupDTO.class);

        CreateGroupRequest createGroupRequest = new CreateGroupRequest();
        createGroupRequest.setName(groupDTO.getName());
        createGroupRequest.setDescription(groupDTO.getDescription());

        when(groupService.createGroup(groupDTOArgumentCaptor.capture())).thenReturn(groupDTO);

        mvc.perform(post("/api/v1/groups")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createGroupRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(groupDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(groupDTO.getName())))
                .andExpect(jsonPath("$.data.description", is(groupDTO.getDescription())))
                .andExpect(jsonPath("$.data.createdAt", notNullValue()))
                .andExpect(jsonPath("$.data.updatedAt", notNullValue()));

        GroupDTO capturedGroupDTO = groupDTOArgumentCaptor.getValue();
        assertThat(capturedGroupDTO.getId()).isNull();
        assertThat(capturedGroupDTO.getName()).isEqualTo(groupDTO.getName());
        assertThat(capturedGroupDTO.getDescription()).isEqualTo(groupDTO.getDescription());
        assertThat(capturedGroupDTO.getCreatedAt()).isNull();
        assertThat(capturedGroupDTO.getUpdatedAt()).isNull();
    }

    @Test
    @DisplayName("Get list of groups")
    void getListOfGroups() throws Exception {

        when(groupService.getGroups()).thenReturn(Collections.singletonList(groupDTO));

        mvc.perform(get("/api/v1/groups")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0]", notNullValue()))
                .andExpect(jsonPath("$.data[0].id", is(groupDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(groupDTO.getName())))
                .andExpect(jsonPath("$.data[0].description", is(groupDTO.getDescription())))
                .andExpect(jsonPath("$.data[0].createdAt", notNullValue()))
                .andExpect(jsonPath("$.data[0].updatedAt", notNullValue()));
    }

    @Test
    @DisplayName("Update group")
    void updateGroup() throws Exception {
        ArgumentCaptor<GroupDTO> groupDTOArgumentCaptor = ArgumentCaptor.forClass(GroupDTO.class);

        UpdateGroupRequest updateGroupRequest = new UpdateGroupRequest();
        updateGroupRequest.setName(groupDTO.getName());
        updateGroupRequest.setDescription(groupDTO.getDescription());

        when(groupService.updateGroup(groupDTOArgumentCaptor.capture())).thenReturn(groupDTO);

        mvc.perform(put("/api/v1/groups/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(updateGroupRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.ACCEPTED.value())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(groupDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(groupDTO.getName())))
                .andExpect(jsonPath("$.data.description", is(groupDTO.getDescription())))
                .andExpect(jsonPath("$.data.createdAt", notNullValue()))
                .andExpect(jsonPath("$.data.updatedAt", notNullValue()));

        GroupDTO capturedGroupDTO = groupDTOArgumentCaptor.getValue();
        assertThat(capturedGroupDTO.getId()).isEqualTo(1L);
        assertThat(capturedGroupDTO.getName()).isEqualTo(groupDTO.getName());
        assertThat(capturedGroupDTO.getDescription()).isEqualTo(groupDTO.getDescription());
        assertThat(capturedGroupDTO.getCreatedAt()).isNull();
        assertThat(capturedGroupDTO.getUpdatedAt()).isNull();
    }

}