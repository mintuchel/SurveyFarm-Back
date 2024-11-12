package notblank.surveyfarm.domain.user.service;

import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.user.entity.User;
import notblank.surveyfarm.domain.user.repository.UserRepository;
import notblank.surveyfarm.global.exception.errorcode.UserErrorCode;
import notblank.surveyfarm.global.exception.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(int uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
    }
}
