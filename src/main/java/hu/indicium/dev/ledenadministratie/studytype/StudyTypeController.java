//package hu.indicium.dev.ledenadministratie.studytype;
//
//import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
//import hu.indicium.dev.ledenadministratie.studytype.requests.CreateStudyTypeRequest;
//import hu.indicium.dev.ledenadministratie.util.Response;
//import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//import static hu.indicium.dev.ledenadministratie.util.BaseUrl.API_V1;
//
//@RestController
//@RequestMapping(API_V1 + "/studytypes")
//public class StudyTypeController {
//
//    private final StudyTypeService studyTypeService;
//
//    public StudyTypeController(StudyTypeService studyTypeService) {
//        this.studyTypeService = studyTypeService;
//    }
//
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Response<List<StudyTypeDTO>> getAllStudyTypes() {
//        return ResponseBuilder.ok()
//                .data(studyTypeService.getAllStudyTypes())
//                .build();
//    }
//
//    @GetMapping(value = "/{studyTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Response<StudyTypeDTO> getStudyTypeById(@PathVariable Long studyTypeId) {
//        return ResponseBuilder.ok()
//                .data(studyTypeService.getStudyTypeById(studyTypeId))
//                .build();
//    }
//
//    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public Response<StudyTypeDTO> createStudyType(@RequestBody @Valid CreateStudyTypeRequest createStudyTypeRequest) {
//        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
//        studyTypeDTO.setName(createStudyTypeRequest.getName());
//        return ResponseBuilder.created()
//                .data(studyTypeService.createStudyType(studyTypeDTO))
//                .build();
//    }
//}
