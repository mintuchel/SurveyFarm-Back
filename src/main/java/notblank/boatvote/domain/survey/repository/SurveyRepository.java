package notblank.boatvote.domain.survey.repository;

import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findByOwner(User owner);

    @Query(value = "SELECT * FROM SURVEY WHERE region_code & :participantRegionCode != 0 AND job_code & :participantJobCode != 0 AND age_code & :participantAgeCode != 0 AND gender_code & :participantGenderCode != 0", nativeQuery = true)
    List<Survey> findAvailableSurveyByParticipant(
            @Param("participantRegionCode") int participantRegionCode,
            @Param("participantJobCode") int participantJobCode,
            @Param("participantAgeCode") int participantAgeCode,
            @Param("participantGenderCode") int participantGenderCode
    );

}