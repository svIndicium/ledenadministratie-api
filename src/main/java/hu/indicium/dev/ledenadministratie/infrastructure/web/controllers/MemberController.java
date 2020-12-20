package hu.indicium.dev.ledenadministratie.infrastructure.web.controllers;

import hu.indicium.dev.ledenadministratie.application.commands.ImportMemberCommand;
import hu.indicium.dev.ledenadministratie.application.query.MemberQueryService;
import hu.indicium.dev.ledenadministratie.application.service.MemberService;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.MemberDto;
import hu.indicium.dev.ledenadministratie.util.BaseUrl;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(BaseUrl.API_V1)
@Slf4j
public class MemberController {
    private final MemberQueryService memberQueryService;

    private final MemberService memberService;

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

    @PostMapping("/members/import")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<MemberDto> importMember(@RequestBody ImportMemberCommand importMemberCommand) {
        MemberId memberId = memberService.importMember(importMemberCommand);
        Member member = memberQueryService.getMemberById(memberId);
        MemberDto memberDto = new MemberDto(member);
        return ResponseBuilder.ok()
                .data(memberDto)
                .build();
    }
}
