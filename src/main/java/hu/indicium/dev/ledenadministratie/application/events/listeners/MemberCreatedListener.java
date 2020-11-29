package hu.indicium.dev.ledenadministratie.application.events.listeners;

import hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberCreated;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailListType;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberCreatedListener implements DomainEventSubscriber<MemberCreated> {

    private final MailService mailService;

    @Override
    public void handleEvent(MemberCreated event) {
        MailAddress mailAddress = event.getMember().getMailAddresses().get(0);
        Name name = event.getMember().getMemberDetails().getName();
        mailService.addMailAddressToMailingList(mailAddress, name, MailListType.GENERAL);
        if (mailAddress.isReceivesNewsletter()) {
            mailService.addMailAddressToMailingList(mailAddress, name, MailListType.NEWSLETTER);
        }
    }

    @Override
    public Class<MemberCreated> subscribedToEventType() {
        return MemberCreated.class;
    }
}
