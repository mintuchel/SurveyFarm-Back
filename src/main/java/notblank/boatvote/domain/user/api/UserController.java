package notblank.boatvote.domain.user.api;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
