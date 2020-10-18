package hu.indicium.dev.ledenadministratie.infrastructure.web.controllers;

import hu.indicium.dev.ledenadministratie.application.commands.NewStudyTypeCommand;
import hu.indicium.dev.ledenadministratie.application.query.StudyTypeQueryService;
import hu.indicium.dev.ledenadministratie.application.service.StudyTypeService;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.util.BaseUrl;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(BaseUrl.API_V1)
@RestController
public class StudyTypeController {
    private final StudyTypeService studyTypeService;

    private final StudyTypeQueryService studyTypeQueryService;

    @PostMapping("/studytypes")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<StudyTypeDTO> createStudyType(@RequestBody NewStudyTypeCommand newStudyTypeCommand) {
        StudyTypeId studyTypeId = studyTypeService.createStudyType(newStudyTypeCommand);
        StudyType studyType = studyTypeQueryService.getStudyTypeById(studyTypeId);
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO(studyType);
        return ResponseBuilder.created()
                .data(studyTypeDTO)
                .build();
    }
}
