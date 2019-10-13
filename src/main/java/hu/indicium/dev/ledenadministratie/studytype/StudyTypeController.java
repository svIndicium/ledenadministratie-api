package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/studytype")
public class StudyTypeController {

    private final StudyTypeService studyTypeService;

    private final StudyTypeMapper studyTypeMapper;

    public StudyTypeController(StudyTypeService studyTypeService, StudyTypeMapper studyTypeMapper) {
        this.studyTypeService = studyTypeService;
        this.studyTypeMapper = studyTypeMapper;
    }

    @GetMapping
    public List<StudyTypeDTO> getAllStudyTypes() {
        return studyTypeService.getAllStudyTypes();
    }
}
