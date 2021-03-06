package hu.indicium.dev.ledenadministratie.infrastructure.web.controllers;

import hu.indicium.dev.ledenadministratie.application.commands.NewStudyTypeCommand;
import hu.indicium.dev.ledenadministratie.application.query.StudyTypeQueryService;
import hu.indicium.dev.ledenadministratie.application.service.StudyTypeService;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.StudyTypeDto;
import hu.indicium.dev.ledenadministratie.util.BaseUrl;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestMapping(BaseUrl.API_V1)
@RestController
public class StudyTypeController {
    private final StudyTypeService studyTypeService;

    private final StudyTypeQueryService studyTypeQueryService;

    @PostMapping("/studytypes")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<StudyTypeDto> createStudyType(@RequestBody NewStudyTypeCommand newStudyTypeCommand) {
        StudyTypeId studyTypeId = studyTypeService.createStudyType(newStudyTypeCommand);
        StudyType studyType = studyTypeQueryService.getStudyTypeById(studyTypeId);
        StudyTypeDto studyTypeDTO = new StudyTypeDto(studyType);
        return ResponseBuilder.created()
                .data(studyTypeDTO)
                .build();
    }

    @GetMapping("/studytypes")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<StudyTypeDto>> getStudyTypes() {
        Collection<StudyType> studyTypes = studyTypeQueryService.getStudyTypes();
        Collection<StudyTypeDto> studyTypeDtos = studyTypes.stream()
                .map(StudyTypeDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(studyTypeDtos)
                .build();
    }

    @GetMapping("/studytypes/{studyTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<StudyTypeDto> getStudyTypeByStudyTypeId(@PathVariable UUID studyTypeId) {
        StudyTypeId typeId = StudyTypeId.fromId(studyTypeId);
        StudyType studyTypes = studyTypeQueryService.getStudyTypeById(typeId);
        return ResponseBuilder.ok()
                .data(studyTypes)
                .build();
    }
}
