package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.application.commands.NewStudyTypeCommand;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;

public interface StudyTypeService {
    StudyTypeId createStudyType(NewStudyTypeCommand newStudyTypeCommand);
}
