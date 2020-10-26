package hu.indicium.dev.ledenadministratie.infrastructure.web.controllers;

import hu.indicium.dev.ledenadministratie.application.query.MemberQueryService;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.MemberDto;
import hu.indicium.dev.ledenadministratie.util.BaseUrl;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(BaseUrl.API_V1)
public class MemberController {
    private final MemberQueryService memberQueryService;

    @GetMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<MemberDto>> getAllMembers() {
        Collection<Member> members = memberQueryService.getAllMembers();
        Set<MemberDto> memberDtos = members.stream()
                .map(MemberDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(memberDtos)
                .build();
    }

    @GetMapping("/members/{authId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<MemberDto> getMemberByAuthId(@PathVariable String authId) {
        MemberId memberId = MemberId.fromAuthId(authId);
        Member member = memberQueryService.getMemberById(memberId);
        MemberDto memberDto = new MemberDto(member);
        return ResponseBuilder.ok()
                .data(memberDto)
                .build();
    }
}
