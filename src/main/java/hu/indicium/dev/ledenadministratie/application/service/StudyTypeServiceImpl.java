package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.application.commands.NewStudyTypeCommand;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudyTypeServiceImpl implements StudyTypeService {

    private final StudyTypeRepository studyTypeRepository;

    @Override
    @PreAuthorize("hasPermission('write:study_type')")
    public StudyTypeId createStudyType(NewStudyTypeCommand newStudyTypeCommand) {
        StudyTypeId studyTypeId = studyTypeRepository.nextIdentity();

        StudyType studyType = new StudyType(studyTypeId, newStudyTypeCommand.getShortName(), newStudyTypeCommand.getName());

        studyTypeRepository.save(studyType);

        return studyTypeId;
    }
}
