package hu.indicium.dev.ledenadministratie.studytype;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.studytype.requests.CreateStudyTypeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudyTypeController.class)
@DisplayName("Study Type Controller")
@Tag("Controller")
class StudyTypeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private StudyTypeService studyTypeService;

    @Autowired
    private MockMvc mvc;

    private StudyTypeDTO studyTypeDTO;

    @BeforeEach
    void setUp() {
        studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setName("SD");
        studyTypeDTO.setId(1L);
    }

    @Test
    @DisplayName("Get list of study types")
    void shouldReturnListOfStudyTypes() throws Exception {

        when(studyTypeService.getAllStudyTypes()).thenReturn(Collections.singletonList(studyTypeDTO));

        mvc.perform(get("/studytypes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data[0].id", is(studyTypeDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(studyTypeDTO.getName())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())));
    }

    @Test
    @DisplayName("Get studytype by id")
    void shouldReturnJsonObject_whenGetStudyTypeById() throws Exception {

        when(studyTypeService.getStudyTypeById(eq(1L))).thenReturn(studyTypeDTO);

        mvc.perform(get("/studytypes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(studyTypeDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(studyTypeDTO.getName())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())));
    }

    @Test
    @DisplayName("Create study type")
    void shouldAddStudyType() throws Exception {

        CreateStudyTypeRequest createStudyTypeRequest = new CreateStudyTypeRequest();
        createStudyTypeRequest.setName(studyTypeDTO.getName());

        given(studyTypeService.createStudyType(any(StudyTypeDTO.class))).willReturn(studyTypeDTO);

        mvc.perform(post("/studytypes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createStudyTypeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(studyTypeDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is(studyTypeDTO.getName())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())));
    }

}