package notblank.boatvote.domain.user.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.repository.UserRepository;
import notblank.boatvote.global.exception.errorcode.UserErrorCode;
import notblank.boatvote.global.exception.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
