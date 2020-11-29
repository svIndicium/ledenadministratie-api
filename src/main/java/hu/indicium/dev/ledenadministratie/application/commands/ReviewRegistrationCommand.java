package hu.indicium.dev.ledenadministratie.application.commands;

import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRegistrationCommand {

    private UUID registrationId;

    private ReviewStatus reviewStatus;

    private String comment;
}
