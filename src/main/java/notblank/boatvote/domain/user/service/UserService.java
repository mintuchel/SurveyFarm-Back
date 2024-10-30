package notblank.boatvote.domain.user.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.repository.UserRepository;
import notblank.boatvote.global.exception.errorcode.UserErrorCode;
import notblank.boatvote.global.exception.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findByNickName(String nickName) {
        return userRepository.findByNickName(nickName)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
    }
}
