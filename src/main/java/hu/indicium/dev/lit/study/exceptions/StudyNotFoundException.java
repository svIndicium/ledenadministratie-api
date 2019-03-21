package hu.indicium.dev.lit.study.exceptions;

public class StudyNotFoundException extends RuntimeException {
    public StudyNotFoundException() {
        super("Study not found!");
    }
}
