package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.studytype.requests.CreateStudyTypeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/studytype")
public class StudyTypeController {

    private final StudyTypeService studyTypeService;


    public StudyTypeController(StudyTypeService studyTypeService) {
        this.studyTypeService = studyTypeService;
    }

    @GetMapping
    public List<StudyTypeDTO> getAllStudyTypes() {
        return studyTypeService.getAllStudyTypes();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public StudyTypeDTO createStudyType(@RequestBody @Valid CreateStudyTypeRequest createStudyTypeRequest) {
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setName(createStudyTypeRequest.getName());
        return studyTypeService.createStudyType(studyTypeDTO);
    }
}
