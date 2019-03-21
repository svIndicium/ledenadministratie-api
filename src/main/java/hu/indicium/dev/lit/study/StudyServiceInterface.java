package hu.indicium.dev.lit.study;

import java.util.List;

public interface StudyServiceInterface {
    List<Study> getStudiesByUserId(Long userId);

    Study createStudy(Study study);

    Study updateStudy(Study study);
}
