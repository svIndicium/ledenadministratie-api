package hu.indicium.dev.lit.membership;

import hu.indicium.dev.lit.membership.dto.NewMembershipDTO;
import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import hu.indicium.dev.lit.user.User;
import hu.indicium.dev.lit.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MembershipController {
    private final MembershipServiceInterface membershipService;

    private final UserServiceInterface userService;

    @Autowired
    public MembershipController(MembershipServiceInterface membershipService, UserServiceInterface userService) {
        this.membershipService = membershipService;
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/memberships")
    public Response getMembershipsByUserId(@PathVariable Long userId) {
        return new SuccessResponse(membershipService.getMembershipsByUserId(userId));
    }

    @PostMapping("/users/{userId}/memberships")
    public Response addMembership(@PathVariable Long userId, @RequestBody NewMembershipDTO newMembershipDTO) {
        User user = userService.getUserById(userId);
        return new SuccessResponse(membershipService.createMembership(newMembershipDTO.getStartDate(), newMembershipDTO.getEndDate(), user));
    }
}
