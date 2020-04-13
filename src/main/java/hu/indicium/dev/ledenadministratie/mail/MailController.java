package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.requests.MailVerificationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/mail/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyMailAddress(@RequestBody MailVerificationRequest mailVerificationRequest) {
        mailService.verifyMail(mailVerificationRequest.getAddress(), mailVerificationRequest.getToken());
    }
}
