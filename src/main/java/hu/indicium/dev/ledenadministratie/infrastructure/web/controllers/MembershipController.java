package hu.indicium.dev.ledenadministratie.infrastructure.web.controllers;

import hu.indicium.dev.ledenadministratie.application.query.MembershipQueryService;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.MembershipDto;
import hu.indicium.dev.ledenadministratie.util.BaseUrl;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(BaseUrl.API_V1)
public class MembershipController {
    private final MembershipQueryService membershipQueryService;

    @GetMapping("/members/{memberUuid}/memberships")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<MembershipDto>> getMembershipsByMemberId(@PathVariable UUID memberUuid) {
        MemberId memberId = MemberId.fromUuid(memberUuid);
        Collection<Membership> memberships = membershipQueryService.getMembershipsByMemberId(memberId);
        Set<MembershipDto> membershipDtos = memberships.stream()
                .map(MembershipDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(membershipDtos)
                .build();
    }
}
