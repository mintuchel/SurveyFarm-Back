package notblank.boatvote.domain.dummy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class DummyService {
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public void initUser(){
        User user = User.builder()
                .username("mintuchel")
                .password("1234")
                .build();

        em.persist(user);
    }
}
