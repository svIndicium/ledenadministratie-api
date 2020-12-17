package hu.indicium.dev.ledenadministratie.infrastructure.web.controllers;

import hu.indicium.dev.ledenadministratie.application.query.MemberQueryService;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.MailAddressDto;
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
public class MailAddressController {

    private final MemberQueryService memberQueryService;

    @GetMapping("/members/{authId}/mailaddresses")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<MailAddressDto>> getMailAddressByAuthId(@PathVariable String authId) {
        MemberId memberId = MemberId.fromAuthId(authId);
        Member member = memberQueryService.getMemberById(memberId);
        Collection<MailAddress> mailAddresses = member.getMailAddresses();
        Set<MailAddressDto> mailAddressDtos = mailAddresses.stream()
                .map(MailAddressDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.ok()
                .data(mailAddressDtos)
                .build();
    }
}
