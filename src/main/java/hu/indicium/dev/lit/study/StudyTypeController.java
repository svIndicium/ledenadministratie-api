package hu.indicium.dev.lit.study;

import hu.indicium.dev.lit.response.DeleteSuccessResponse;
import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import hu.indicium.dev.lit.study.dto.NewStudyTypeDTO;
import hu.indicium.dev.lit.study.dto.StudyTypeDTO;
import hu.indicium.dev.lit.study.dto.UpdateStudyTypeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class StudyTypeController {

    private final StudyTypeServiceInterface studyTypeService;

    private final ModelMapper modelMapper;

    @Autowired
    public StudyTypeController(StudyTypeServiceInterface studyTypeService, ModelMapper modelMapper) {
        this.studyTypeService = studyTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/studytypes")
    @PreAuthorize("hasPermission('read:study_types')")
    public Response getAllStudyTypes() {
        return new SuccessResponse(studyTypeService.getAllStudyTypes()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/studytypes")
    @PreAuthorize("hasPermission('create:study_types')")
    public Response createStudyType(@RequestBody NewStudyTypeDTO studyTypeDTO) {
        return new SuccessResponse(studyTypeService.createStudyType(convertToEntity(studyTypeDTO)));
    }

    @PutMapping("/studytypes/{studyTypeId}")
    @PreAuthorize("hasPermission('edit:study_types')")
    public Response updateStudyType(@PathVariable Long studyTypeId, @RequestBody UpdateStudyTypeDTO studyTypeDTO) {
        StudyType studyType = convertToEntity(studyTypeDTO);
        studyType.setId(studyTypeId);
        return new SuccessResponse(convertToDTO(studyTypeService.updateStudyType(studyType)));
    }

    @DeleteMapping("/studytypes/{studyTypeId}")
    @PreAuthorize("hasPermission('delete:study_types')")
    public Response deleteStudyTypes(@PathVariable Long studyTypeId) {
        studyTypeService.deleteStudyType(studyTypeId);
        return new DeleteSuccessResponse();
    }

    private StudyTypeDTO convertToDTO(StudyType studyType) {
        return modelMapper.map(studyType, StudyTypeDTO.class);
    }

    private StudyType convertToEntity(NewStudyTypeDTO studyTypeDTO) {
        return modelMapper.map(studyTypeDTO, StudyType.class);
    }

    private StudyType convertToEntity(UpdateStudyTypeDTO studyTypeDTO) {
        return modelMapper.map(studyTypeDTO, StudyType.class);
    }
}
