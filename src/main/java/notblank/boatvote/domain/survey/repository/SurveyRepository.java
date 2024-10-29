package notblank.boatvote.domain.survey.repository;

import jakarta.transaction.Transactional;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findByOwner(User owner);

    // 업데이트된 행 수를 반환
    // 성공이면 1 실패하면 0 반환
    @Modifying
    @Transactional
    @Query(value = "UPDATE SURVEY SET current_head_cnt = current_head_cnt + 1 WHERE id = :sid AND current_head_cnt + 1 <= max_head_cnt", nativeQuery = true)
    void incrementCurrentHeadCnt(@Param("sid") int sid);

    // 특정 유저가 참여가능한 설문 조회
    @Query(value = "SELECT * FROM SURVEY WHERE region_code & :participantRegionCode != 0 AND job_code & :participantJobCode != 0 AND age_code & :participantAgeCode != 0 AND gender_code & :participantGenderCode != 0", nativeQuery = true)
    List<Survey> findAvailableSurveyByParticipant(
            @Param("participantRegionCode") int participantRegionCode,
            @Param("participantJobCode") int participantJobCode,
            @Param("participantAgeCode") int participantAgeCode,
            @Param("participantGenderCode") int participantGenderCode
    );

}