package notblank.surveyfarm.domain.user.service;

import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.user.dto.response.UserResponse;
import notblank.surveyfarm.domain.user.entity.User;
import notblank.surveyfarm.domain.user.repository.UserRepository;
import notblank.surveyfarm.domain.utility.DTOConverter;
import notblank.surveyfarm.global.exception.errorcode.UserErrorCode;
import notblank.surveyfarm.global.exception.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final DTOConverter dtoConverter;

    @Transactional(readOnly = true)
    public UserResponse getUserResponseById(int uid) {
        User user = findById(uid);
        return dtoConverter.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public User findById(int uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND));
    }
}
