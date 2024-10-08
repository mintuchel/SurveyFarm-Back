package notblank.boatvote.domain.survey.repository;

import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findByOwner(User owner);

    @Query(value = "SELECT * FROM SURVEY WHERE region_code & :user_region_code != 0 AND jobcode & :user_job_code != 0 AND agecode & :user_age_code != 0 ", nativeQuery = true)
    Optional<Survey> findAvailableSurveyByParticipant(@Param("user_region_code") int user_region_code,
                                                      @Param("user_job_code") int user_job_code,
                                                      @Param("user_age_code") int user_age_code);
}