package hu.indicium.dev.ledenadministratie.application.events.listeners;

import hu.indicium.dev.ledenadministratie.application.service.MemberService;
import hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationApproved;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegistrationApprovedListener implements DomainEventSubscriber<RegistrationApproved> {

    private final MemberService memberService;

    private final AuthService authService;

    @Override
    public void handleEvent(RegistrationApproved event) {
        memberService.registerMember(event.getRegistration().getRegistrationId());
        authService.moveUserToGroup(event.getRegistration().getRegistrationId().getId(), "Leden");
    }

    @Override
    public Class<RegistrationApproved> subscribedToEventType() {
        return RegistrationApproved.class;
    }
}
