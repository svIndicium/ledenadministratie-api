package hu.indicium.dev.lit.membership;

import hu.indicium.dev.lit.membership.dto.MembershipDTO;
import hu.indicium.dev.lit.membership.dto.NewMembershipDTO;
import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class MembershipController {
    private final MembershipServiceInterface membershipService;

    private final ModelMapper modelMapper;

    @Autowired
    public MembershipController(MembershipServiceInterface membershipService, ModelMapper modelMapper) {
        this.membershipService = membershipService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/{userId}/memberships")
    @PreAuthorize("(hasPermission('read:memberships') && isUser(#userId)) || hasPermission('read:all_memberships')")
    public Response getMembershipsByUserId(@PathVariable Long userId) {
        return new SuccessResponse(membershipService.getMembershipsByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/users/{userId}/memberships")
    @PreAuthorize("hasPermission('create:memberships')")
    public Response addMembership(@PathVariable Long userId, @RequestBody NewMembershipDTO newMembershipDTO) {
        return new SuccessResponse(convertToDTO(membershipService.createMembership(convertToEntity(newMembershipDTO), userId)));
    }

    private Membership convertToEntity(NewMembershipDTO membershipDTO) {
        return modelMapper.map(membershipDTO, Membership.class);
    }

    private MembershipDTO convertToDTO(Membership membership) {
        return modelMapper.map(membership, MembershipDTO.class);
    }
}
