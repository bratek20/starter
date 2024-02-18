package pl.bratek20.commons.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bratek20.commons.user.api.User;
import pl.bratek20.commons.user.api.UserApi;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserApi api;

    @PostMapping("/create")
    public Long create(@RequestBody User user) {
        return api.create(user).value();
    }

    @PostMapping("/getIdentityId")
    public Long getIdentityId(@RequestBody User user) {
        return api.getIdentityId(user).value();
    }
}
