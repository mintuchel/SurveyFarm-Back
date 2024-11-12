package notblank.surveyfarm.domain.survey.repository;

import jakarta.transaction.Transactional;
import notblank.surveyfarm.domain.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

    // 업데이트된 행 수를 반환
    // 성공이면 1 실패하면 0 반환
    @Modifying
    @Transactional
    @Query(value = "UPDATE SURVEY SET current_head_cnt = current_head_cnt + 1 WHERE id = :sid AND current_head_cnt + 1 <= max_head_cnt", nativeQuery = true)
    void incrementCurrentHeadCnt(@Param("sid") int sid);

    // 특정 유저가 참여가능한 설문 조회
    @Query(value = "SELECT * FROM SURVEY WHERE region_code & :participantRegionCode = region_code AND job_code & :participantJobCode = job_code AND age_code & :participantAgeCode = age_code AND gender_code & :participantGenderCode = gender_code", nativeQuery = true)
    List<Survey> getAvailableSurveyByParticipant(
            @Param("participantRegionCode") int participantRegionCode,
            @Param("participantJobCode") int participantJobCode,
            @Param("participantAgeCode") int participantAgeCode,
            @Param("participantGenderCode") int participantGenderCode
    );

    // 마감임박 설문 조회
//    @Query(value = "SELECT * FROM SURVEY WHERE DATEDIFF(end_at, CURDATE())", nativeQuery = true)
//    List<Survey> getDeadLineSurveys();

    @Query(value = "SELECT * FROM DEAD_LINE_SURVEY", nativeQuery = true)
    List<Survey> getDeadLineSurveys();

    // 특정 유저가 의뢰한 설문 조회
    @Query(value = "SELECT * FROM SURVEY WHERE uid = :uid", nativeQuery = true)
    List<Survey> getRequestedSurvey(@Param("uid") int uid);


}