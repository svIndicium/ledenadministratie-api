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
        // TODO make group id variable
        authService.moveUserToGroup(event.getRegistration().getRegistrationId().getId(), "33544d70-7dd5-49b7-ae56-cad5471db4ef");
    }

    @Override
    public Class<RegistrationApproved> subscribedToEventType() {
        return RegistrationApproved.class;
    }
}
