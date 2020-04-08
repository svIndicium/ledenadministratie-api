package hu.indicium.dev.ledenadministratie.registration.validation;

import hu.indicium.dev.ledenadministratie.registration.Registration;
import hu.indicium.dev.ledenadministratie.util.Validator;

public class CommentValidator implements Validator<Registration> {
    public static final String ERROR_MESSAGE = "Comment is empty";

    @Override
    public void validate(Registration registration) {
        if (registration.getFinalizedAt() != null) {
            if (!registration.isApproved()) {
                if (registration.getComment() == null || registration.getComment().isBlank()) {
                    throw new IllegalArgumentException(ERROR_MESSAGE);
                }
            }
        }
    }
}
