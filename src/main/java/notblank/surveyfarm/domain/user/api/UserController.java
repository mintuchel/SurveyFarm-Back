package notblank.surveyfarm.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.user.dto.response.UserResponse;
import notblank.surveyfarm.domain.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 API", description = "로그인, 회원가입, 유저 조회")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "유저 단건 조회")
    public UserResponse getUserById(@PathVariable int id){
        return userService.getUserResponseById(id);
    }
}
