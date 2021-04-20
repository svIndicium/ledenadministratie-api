package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import hu.indicium.dev.ledenadministratie.domain.DomainEventPublisher;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.util.TestDomainEventSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Registration")
class RegistrationTest {

    private Date startDate;

    private RegistrationId registrationId;

    private Name name;

    private StudyTypeId studyTypeId;

    private MemberDetails memberDetails;

    private MailAddress mailAddress;

    private TestDomainEventSubscriber eventSubscriber;

    @BeforeEach
    void setUp() {
        DomainEventPublisher.instance().reset();
        eventSubscriber = TestDomainEventSubscriber.subscribe();
        this.startDate = new Date();
        this.registrationId = RegistrationId.fromId(UUID.randomUUID());
        this.name = new Name("Miguel", "Don", "Gomez");
        this.studyTypeId = StudyTypeId.fromId(UUID.randomUUID());
        StudyType studyType = new StudyType(studyTypeId, "SD", "Software Development");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
        this.memberDetails = new MemberDetails(name, "+31612345678", calendar.getTime(), studyType);
        this.mailAddress = new MailAddress("mdg@example.com", true);
        this.eventSubscriber.clear();
    }

    @Test
    @DisplayName("Create registration")
    void createRegistration() {
        Registration registration = new Registration(registrationId, memberDetails, mailAddress);

        assertThat(registration).isNotNull();
        assertThat(registration.getRegistrationId()).isEqualTo(registrationId);
        assertThat(registration.getMemberDetails()).isEqualTo(memberDetails);
        assertThat(registration.getMailAddress()).isEqualTo(mailAddress);
        assertThat(registration.getReviewStatus()).isEqualTo(ReviewStatus.PENDING);
        assertThat(registration.getMember()).isNull();
        assertThat(registration.getReviewDetails()).isNull();
    }

    @Test
    @DisplayName("Verify emitted event when creating registration")
    void verifyEmittedEvent_whenCreateRegistration() {
        new Registration(registrationId, memberDetails, mailAddress);

        List<DomainEvent> emittedEvents = eventSubscriber.getEvents();

        List<RegistrationCreated> emittedRegistrationCreatedEvents = emittedEvents.stream()
                .filter(domainEvent -> domainEvent instanceof RegistrationCreated)
                .map(domainEvent -> (RegistrationCreated) domainEvent)
                .collect(Collectors.toList());

        assertThat(emittedRegistrationCreatedEvents).hasSize(1);

        RegistrationCreated registrationCreated = emittedRegistrationCreatedEvents.get(0);

        assertThat(registrationCreated.occurredOn()).isBeforeOrEqualsTo(new Date());
        assertThat(registrationCreated.occurredOn()).isCloseTo(startDate, 1000);
        assertThat(registrationCreated.getCreatedAt()).isAfterOrEqualsTo(memberDetails.getCreatedAt());
        assertThat(registrationCreated.eventVersion()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @DisplayName("Approve registration")
    void approveRegistration() {
        Registration registration = new Registration(registrationId, memberDetails, mailAddress);

        String reviewedBy = "Secretary's name";

        registration.approve(reviewedBy);

        ReviewDetails reviewDetails = registration.getReviewDetails();
        assertThat(reviewDetails.getReviewedBy()).isEqualTo(reviewedBy);
        assertThat(reviewDetails.getReviewedAt()).isCloseTo(startDate, 100);
        assertThat(reviewDetails.getComment()).isNull();
        assertThat(registration.getReviewStatus()).isEqualTo(ReviewStatus.APPROVED);
    }

    @Test
    @DisplayName("Should throw exception when approving an already reviewed registration")
    void shouldThrowException_whenApprovingAnAlreadyReviewedRegistration() {

        Registration registration = new Registration(registrationId, memberDetails, mailAddress);

        String reviewedBy = "Secretary's name";

        registration.approve(reviewedBy);

        try {
            registration.approve(reviewedBy);
            fail("Should throw exception because registration was already reviewed");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RegistrationAlreadyReviewedException.class);
            RegistrationAlreadyReviewedException exception = (RegistrationAlreadyReviewedException) e;
            assertThat(exception.getRegistrationId()).isEqualTo(registrationId);
            assertThat(exception.getReviewedAt()).isAfterOrEqualsTo(startDate);
            assertThat(exception.getReviewedAt()).isCloseTo(startDate, 100);
            assertThat(exception.getReviewedBy()).isEqualTo(reviewedBy);
        }
    }

    @Test
    @DisplayName("Deny a registration")
    void denyRegistration() {

        Registration registration = new Registration(registrationId, memberDetails, mailAddress);

        String reviewedBy = "Secretary's name";
        String comment = "troll";

        registration.deny(reviewedBy, comment);

        assertThat(registration.getReviewStatus()).isEqualTo(ReviewStatus.DENIED);
        assertThat(registration.getReviewDetails()).isNotNull();
        assertThat(registration.getReviewDetails().getComment()).isEqualTo(comment);
        assertThat(registration.getReviewDetails().getReviewedBy()).isEqualTo(reviewedBy);
        assertThat(registration.getReviewDetails().getReviewedAt()).isAfterOrEqualsTo(startDate);
        assertThat(registration.getReviewDetails().getReviewedAt()).isCloseTo(startDate, 100);
    }

    @Test
    @DisplayName("Should throw exception when denying an already reviewed registration")
    void shouldThrowException_whenDenyingAnAlreadyReviewedRegistration() {
        Registration registration = new Registration(registrationId, memberDetails, mailAddress);

        String reviewedBy = "Secretary's name";
        String comment = "troll";

        registration.deny(reviewedBy, comment);

        try {
            registration.deny(reviewedBy, comment);
            fail("Should throw exception because registration was already reviewed");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RegistrationAlreadyReviewedException.class);
            RegistrationAlreadyReviewedException exception = (RegistrationAlreadyReviewedException) e;
            assertThat(exception.getRegistrationId()).isEqualTo(registrationId);
            assertThat(exception.getReviewedAt()).isAfterOrEqualsTo(startDate);
            assertThat(exception.getReviewedAt()).isCloseTo(startDate, 100);
            assertThat(exception.getReviewedBy()).isEqualTo(reviewedBy);
        }
    }

    @Test
    @DisplayName("Registration no-args constructor")
    void createRegistrationWithNoArgs() {
        new Registration();
    }
}