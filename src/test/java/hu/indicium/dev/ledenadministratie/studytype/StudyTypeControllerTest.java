package hu.indicium.dev.ledenadministratie.studytype;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.studytype.requests.CreateStudyTypeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @DisplayName("Get list of study types")
    void shouldReturnListOfStudyTypes() throws Exception {
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setName("SD");
        studyTypeDTO.setId(1L);

        StudyTypeDTO studyTypeDTO1 = new StudyTypeDTO();
        studyTypeDTO1.setName("TI");
        studyTypeDTO1.setId(2L);

        when(studyTypeService.getAllStudyTypes()).thenReturn(Arrays.asList(studyTypeDTO, studyTypeDTO1));

        mvc.perform(get("/studytype")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(studyTypeDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(studyTypeDTO.getName())))
                .andExpect(jsonPath("$[1].id", is(studyTypeDTO1.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(studyTypeDTO1.getName())));
    }

    @Test
    @DisplayName("Create study type")
    void shouldAddStudyType() throws Exception {
        StudyType studyType = new StudyType("SD");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        CreateStudyTypeRequest createStudyTypeRequest = toCreateStudyTypeRequest(studyTypeDTO);

        given(studyTypeService.createStudyType(any(StudyTypeDTO.class))).willReturn(studyTypeDTO);

        mvc.perform(post("/studytype")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createStudyTypeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(studyType.getId().intValue())))
                .andExpect(jsonPath("$.name", is(studyType.getName())));
    }

    private CreateStudyTypeRequest toCreateStudyTypeRequest(StudyTypeDTO studyTypeDTO) {
        CreateStudyTypeRequest createStudyTypeRequest = new CreateStudyTypeRequest();
        createStudyTypeRequest.setName(studyTypeDTO.getName());
        return createStudyTypeRequest;
    }

}