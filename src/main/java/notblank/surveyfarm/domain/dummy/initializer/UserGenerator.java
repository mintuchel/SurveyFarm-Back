package notblank.surveyfarm.domain.dummy.initializer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGenerator {
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public void generateUsers(){
        initUser();
        initEnterprise();
        initOrganization();
    }

    private User buildUser(String nickName, String password, int regionCode, int jobCode, int ageCode, int genderCode){
        return User.builder()
                .nickName(nickName)
                .password(password)
                .regionCode(regionCode)
                .jobCode(jobCode)
                .ageCode(ageCode)
                .genderCode(genderCode)
                .build();
    }

    private void initUser(){
        em.persist(buildUser("mintuchel", "7", 1, 262144, 2, 1));   // 서울, 미디어·문화·스포츠, 20대, 남자
        em.persist(buildUser("huro0906", "11", 1024, 32, 2, 1));     // 대구, 개발·데이터, 20대, 남자
        em.persist(buildUser("shymirr", "9", 66536, 32, 2, 2));      // 제주, 개발·데이터, 20대, 여자
    }

    private void initEnterprise(){
        em.persist(buildUser("Netflix_KR", "100", 0, 262144, 0, 0)); // 전체, 미디어·문화·스포츠, 전체 연령, 전체 성별
        em.persist(buildUser("Spotify_KR", "100", 0, 262144, 0, 0)); // 전체, 미디어·문화·스포츠, 전체 연령, 전체 성별
        em.persist(buildUser("Toss", "100", 0, 262144, 0, 0));
        em.persist(buildUser("KoreanAir", "100", 0, 262144, 0, 0));
    }

    private void initOrganization(){
        em.persist(buildUser("KFA", "100", 0, 262144, 0, 0));       // 전체, 미디어·문화·스포츠, 전체 연령, 전체 성별
        em.persist(buildUser("OSA", "100", 0, 32, 0, 0)); // 개발·데이터
        em.persist(buildUser("MOF", "100", 0, 32, 0, 0));
    }
}
