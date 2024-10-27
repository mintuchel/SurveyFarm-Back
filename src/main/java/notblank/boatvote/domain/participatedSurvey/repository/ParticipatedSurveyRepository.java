package notblank.boatvote.domain.participatedSurvey.repository;

import notblank.boatvote.domain.participatedSurvey.dto.response.ParticipatedSurveyDTO;
import notblank.boatvote.domain.participatedSurvey.entity.ParticipatedSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipatedSurveyRepository extends JpaRepository<ParticipatedSurvey, Integer> {

    @Query(value = "SELECT sid, participated_at FROM PARTICIPATED_SURVEY WHERE uid = :uid ORDER BY participated_at", nativeQuery = true)
    List<ParticipatedSurveyDTO> findByUid(@Param("uid") int uid);
}
