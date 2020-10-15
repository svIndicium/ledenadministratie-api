package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.validation.StudyTypeNameUniqueValidator;
import hu.indicium.dev.ledenadministratie.util.Validator;
import hu.indicium.dev.ledenadministratie.util.ValidatorGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StudyTypeValidationConfiguration {

    private final StudyTypeService studyTypeService;

    public StudyTypeValidationConfiguration(@Lazy StudyTypeService studyTypeService) {
        this.studyTypeService = studyTypeService;
    }

    @Bean
    Validator<StudyType> studyTypeValidator() {
        return new ValidatorGroup<>(Arrays.asList(new StudyTypeNameUniqueValidator(studyTypeService)));
    }
}
