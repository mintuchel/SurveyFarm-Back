package notblank.boatvote.domain.user.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User API", description = "로그인, 회원가입")
public class UserController {
    private final UserService userService;

}
