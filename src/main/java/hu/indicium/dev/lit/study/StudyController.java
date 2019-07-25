package hu.indicium.dev.lit.study;

import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import hu.indicium.dev.lit.study.dto.NewStudyDTO;
import hu.indicium.dev.lit.study.dto.StudyDTO;
import hu.indicium.dev.lit.study.dto.UpdateStudyDTO;
import hu.indicium.dev.lit.user.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class StudyController {
    private final StudyServiceInterface studyService;

    private final ModelMapper modelMapper;

    private final StudyTypeServiceInterface studyTypeService;

    private final UserServiceInterface userService;

    @Autowired
    public StudyController(StudyServiceInterface studyService, ModelMapper modelMapper, StudyTypeServiceInterface studyTypeService, UserServiceInterface userService) {
        this.studyService = studyService;
        this.modelMapper = modelMapper;
        this.studyTypeService = studyTypeService;
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/studies")
    @PreAuthorize("(hasPermission('read:studies') && isUser(#userId)) || hasPermission('admin:studies')")
    public Response getStudiesByUserId(@PathVariable Long userId) {
        return new SuccessResponse(studyService.getStudiesByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/users/{userId}/studies")
    @PreAuthorize("hasPermission('create:studies')")
    public Response createStudy(@PathVariable Long userId, @RequestBody NewStudyDTO studyDTO) {
        Study study = convertToEntity(studyDTO);
        study.setUser(userService.getUserById(userId));
        return new SuccessResponse(convertToDTO(studyService.createStudy(study)));
    }

    @PutMapping("/users/{userId}/studies/{studyId}")
    @PreAuthorize("hasPermission('edit:studies')")
    public Response updateStudy(@PathVariable Long userId, @PathVariable Long studyId, @RequestBody UpdateStudyDTO studyDTO) {
        Study study = convertToEntity(studyDTO);
        study.setId(studyId);
        return new SuccessResponse(convertToDTO(studyService.updateStudy(study)));
    }

    private StudyDTO convertToDTO(Study study) {
        return modelMapper.map(study, StudyDTO.class);
    }

    private Study convertToEntity(NewStudyDTO studyDTO) {
        Study study = modelMapper.map(studyDTO, Study.class);
        study.setType(studyTypeService.getStudyTypeById(studyDTO.getType().getId()));
        return study;
    }

    private Study convertToEntity(UpdateStudyDTO studyDTO) {
        Study study = modelMapper.map(studyDTO, Study.class);
        study.setType(studyTypeService.getStudyTypeById(studyDTO.getType().getId()));
        return study;
    }
}
