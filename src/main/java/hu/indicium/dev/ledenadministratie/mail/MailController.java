package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.requests.MailVerificationRequest;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static hu.indicium.dev.ledenadministratie.util.BaseUrl.API_V1;

@RestController
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping(API_V1 + "/mail/verify")
    @ResponseStatus(HttpStatus.OK)
    public Response<?> verifyMailAddress(@RequestBody MailVerificationRequest mailVerificationRequest) {
        mailService.verifyMail(mailVerificationRequest.getAddress(), mailVerificationRequest.getToken());
        return ResponseBuilder.ok()
                .build();
    }
}
