package hu.indicium.dev.ledenadministratie.group.validation;

import hu.indicium.dev.ledenadministratie.group.GroupService;
import hu.indicium.dev.ledenadministratie.group.domain.Group;
import hu.indicium.dev.ledenadministratie.group.domain.GroupMember;
import hu.indicium.dev.ledenadministratie.group.dto.GroupMemberDto;
import hu.indicium.dev.ledenadministratie.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("GroupMembership date overlap validator")
class GroupMemberDateOverlapValidatorTest {

    @MockBean
    private GroupService groupService;

    @Autowired
    private GroupMemberDateOverlapValidator groupMemberDateOverlapValidator;

    private GroupMemberDto groupMemberOne;

    private GroupMemberDto groupMemberTwo;

    private GroupMember newGroupMember;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);

        Group group = new Group();
        group.setId(1L);

        this.newGroupMember = new GroupMember();
        newGroupMember.setId(3L);
        newGroupMember.setCreatedAt(new Date());
        newGroupMember.setUpdatedAt(new Date());
        newGroupMember.setUser(user);
        newGroupMember.setGroup(group);

        this.groupMemberOne = new GroupMemberDto();
        groupMemberOne.setId(1L);
        groupMemberOne.setCreatedAt(new Date());
        groupMemberOne.setUpdatedAt(new Date());

        this.groupMemberTwo = new GroupMemberDto();
        groupMemberTwo.setId(2L);
        groupMemberTwo.setCreatedAt(new Date());
        groupMemberTwo.setUpdatedAt(new Date());
    }

    @Test
    @DisplayName("Period 1 is before new period")
    void shouldPass_whenPeriodOneIsBeforeNewPeriod() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.AUGUST, 5));
        groupMemberOne.setEndDate(getDate(2020, Calendar.AUGUST, 20));

        newGroupMember.setStartDate(getDate(2020, Calendar.SEPTEMBER, 5));
        newGroupMember.setEndDate(getDate(2020, Calendar.SEPTEMBER, 20));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("Period 1 is after new period")
    void shouldPass_whenPeriodOneIsAfterNewPeriod() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.SEPTEMBER, 5));
        groupMemberOne.setEndDate(getDate(2020, Calendar.SEPTEMBER, 20));

        newGroupMember.setStartDate(getDate(2020, Calendar.AUGUST, 5));
        newGroupMember.setEndDate(getDate(2020, Calendar.AUGUST, 20));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("Period 1 ends the same day as the new period begins")
    void shouldPass_whenPeriodOneIsDirectlyBeforeNewPeriod() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.SEPTEMBER, 5));
        groupMemberOne.setEndDate(getDate(2020, Calendar.SEPTEMBER, 20));

        newGroupMember.setStartDate(getDate(2020, Calendar.SEPTEMBER, 20));
        newGroupMember.setEndDate(getDate(2020, Calendar.OCTOBER, 30));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("Period 1 starts the same day as the new period ends")
    void shouldPass_whenPeriodOneIsDirectlyAfterNewPeriod() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.OCTOBER, 30));
        groupMemberOne.setEndDate(getDate(2020, Calendar.NOVEMBER, 15));

        newGroupMember.setStartDate(getDate(2020, Calendar.SEPTEMBER, 20));
        newGroupMember.setEndDate(getDate(2020, Calendar.OCTOBER, 30));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("New period starts in period 1")
    void shouldThrow_whenNewPeriodStartsInPeriodOne() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.SEPTEMBER, 5));
        groupMemberOne.setEndDate(getDate(2020, Calendar.SEPTEMBER, 15));

        newGroupMember.setStartDate(getDate(2020, Calendar.SEPTEMBER, 10));
        newGroupMember.setEndDate(getDate(2020, Calendar.OCTOBER, 30));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
            fail("Should throw exception");
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("New period ends in period 1")
    void shouldThrow_whenNewPeriodEndsInPeriodOne() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.OCTOBER, 15));
        groupMemberOne.setEndDate(getDate(2020, Calendar.NOVEMBER, 15));

        newGroupMember.setStartDate(getDate(2020, Calendar.SEPTEMBER, 1));
        newGroupMember.setEndDate(getDate(2020, Calendar.OCTOBER, 30));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
            fail("Should throw exception");
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("New period is in period one")
    void shouldThrow_whenNewPeriodIsInPeriodOne() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.SEPTEMBER, 15));
        groupMemberOne.setEndDate(getDate(2020, Calendar.NOVEMBER, 15));

        newGroupMember.setStartDate(getDate(2020, Calendar.OCTOBER, 1));
        newGroupMember.setEndDate(getDate(2020, Calendar.OCTOBER, 30));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
            fail("Should throw exception");
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("New period is period one")
    void shouldThrow_whenNewPeriodIsPeriodOne() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.SEPTEMBER, 15));
        groupMemberOne.setEndDate(getDate(2020, Calendar.NOVEMBER, 15));

        newGroupMember.setStartDate(getDate(2020, Calendar.SEPTEMBER, 15));
        newGroupMember.setEndDate(getDate(2020, Calendar.NOVEMBER, 15));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
            fail("Should throw exception");
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("New period has period in it")
    void shouldThrow_whenNewPeriodHasPeriodInIt() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.OCTOBER, 15));
        groupMemberOne.setEndDate(getDate(2020, Calendar.OCTOBER, 16));

        newGroupMember.setStartDate(getDate(2020, Calendar.OCTOBER, 1));
        newGroupMember.setEndDate(getDate(2020, Calendar.OCTOBER, 30));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Collections.singletonList(groupMemberOne));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
            fail("Should throw exception");
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    @DisplayName("New period is in between periods")
    void shouldPass_whenNewPeriodIsInBetweenPeriod() {

        groupMemberOne.setStartDate(getDate(2020, Calendar.OCTOBER, 1));
        groupMemberOne.setEndDate(getDate(2020, Calendar.OCTOBER, 10));

        newGroupMember.setStartDate(getDate(2020, Calendar.OCTOBER, 10));
        newGroupMember.setEndDate(getDate(2020, Calendar.OCTOBER, 20));

        groupMemberTwo.setStartDate(getDate(2020, Calendar.OCTOBER, 20));
        groupMemberTwo.setEndDate(getDate(2020, Calendar.OCTOBER, 31));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(Arrays.asList(groupMemberOne, groupMemberTwo));

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @DisplayName("No current periods exist")
    void shouldPass_whenNoExistingPeriodsExist() {

        newGroupMember.setStartDate(getDate(2019, Calendar.OCTOBER, 20));
        newGroupMember.setEndDate(getDate(2019, Calendar.OCTOBER, 20));

        when(groupService.findByGroupIdAndUserId(eq(1L), eq(1L))).thenReturn(new ArrayList<>());

        try {
            groupMemberDateOverlapValidator.validate(newGroupMember);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    private Date getDate(int year, int month, int date) {
        return new GregorianCalendar(year, month, date).getTime();
    }

    @TestConfiguration
    static class DateOverlapValidatorTestContextConfiguration {
        @Autowired
        private GroupService groupService;

        @Bean
        public GroupMemberDateOverlapValidator dateOverlapValidator() {
            return new GroupMemberDateOverlapValidator(groupService);
        }
    }
}