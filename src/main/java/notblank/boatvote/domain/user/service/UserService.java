package notblank.boatvote.domain.user.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Transactional(readOnly = true)
    public User findById(int uid){
        return userRepository.findById(uid).orElseThrow();
    }
}
