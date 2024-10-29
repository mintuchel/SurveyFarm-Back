package notblank.boatvote.domain.participation.repository;

import notblank.boatvote.domain.participation.vo.ParticipationInfoVO;
import notblank.boatvote.domain.participation.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    @Query(value = "SELECT EXISTS (SELECT 1 FROM Participation WHERE uid = :uid AND sid = :sid)", nativeQuery = true)
    Boolean checkIfUserParticipated(@Param("uid") int uid, @Param("sid") int sid);

    // 쿼리문으로 DTO로 바로 매핑하기 위해서는 JPQL을 사용해야함
    // 이 부분 모르겠음 나중에 꼭 찾아보기 JPQL vs native query
    @Query("SELECT new notblank.boatvote.domain.participation.vo.ParticipationInfoVO(p.survey.id, p.participatedAt) FROM Participation p WHERE p.user.id = :uid ORDER BY p.participatedAt")
    List<ParticipationInfoVO> getUserParticipationInfo(@Param("uid") int uid);
}