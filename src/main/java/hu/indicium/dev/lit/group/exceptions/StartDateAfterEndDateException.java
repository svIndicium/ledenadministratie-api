package hu.indicium.dev.lit.group.exceptions;

public class StartDateAfterEndDateException extends RuntimeException {
    public StartDateAfterEndDateException() {
        super("The start date is after the end date!");
    }
}
