package notblank.surveyfarm.domain.participation.repository;

import notblank.surveyfarm.domain.participation.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    // 유저의 설문 참여 여부 확인
    @Query(value = "SELECT EXISTS (SELECT 1 FROM Participation WHERE uid = :uid AND sid = :sid)", nativeQuery = true)
    Integer checkIfUserParticipated(@Param("uid") int uid, @Param("sid") int sid);

    @Query(value = "SELECT sid FROM Participation WHERE uid = :uid",nativeQuery = true)
    List<Integer> getParticipatedSurveyIds(@Param("uid") int uid);

    @Query(value = "SELECT DATE_FORMAT(participatedAt,'%Y-%m-%d') AS participatedAt FROM Participation WHER uid = :uid AND sid = :sid", nativeQuery = true)
    String getParticipatedTime(@Param("uid") int uid, @Param("sid") int sid);
}