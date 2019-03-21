package hu.indicium.dev.lit;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/kek")
    @PreAuthorize("#oauth2.hasScope('read')")
    public String kek() {
        return "kek";
    }
}
