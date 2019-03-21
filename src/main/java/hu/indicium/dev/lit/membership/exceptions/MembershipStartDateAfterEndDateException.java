package hu.indicium.dev.lit.membership.exceptions;

public class MembershipStartDateAfterEndDateException extends RuntimeException {
    public MembershipStartDateAfterEndDateException() {
        super("The start date can't be past the end date!");
    }
}
