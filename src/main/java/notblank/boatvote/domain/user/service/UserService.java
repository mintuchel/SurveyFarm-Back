package notblank.boatvote.domain.user.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


}
