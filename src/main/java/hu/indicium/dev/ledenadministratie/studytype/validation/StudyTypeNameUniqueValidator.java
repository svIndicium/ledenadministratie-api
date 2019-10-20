package hu.indicium.dev.ledenadministratie.studytype.validation;

import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeService;
import hu.indicium.dev.ledenadministratie.util.Validator;

public class StudyTypeNameUniqueValidator implements Validator<StudyType> {

    public static final String ERROR_MESSAGE = "Naam is al in gebruik!";

    private StudyTypeService studyTypeService;

    public StudyTypeNameUniqueValidator(StudyTypeService studyTypeService) {
        this.studyTypeService = studyTypeService;
    }

    @Override
    public void validate(StudyType studyType) {
        if (studyTypeService.isNameInUse(studyType.getName())) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
