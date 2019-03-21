package hu.indicium.dev.lit.study.exceptions;

public class StudyTypeNotFoundException extends RuntimeException {
    public StudyTypeNotFoundException() {
        super("Study type not found!");
    }
}
