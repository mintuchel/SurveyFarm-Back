package notblank.surveyfarm.domain.question.repository;

import notblank.surveyfarm.domain.question.entity.Question;
import notblank.surveyfarm.domain.question.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "SELECT type FROM question WHERE id = :qid", nativeQuery = true)
    QuestionType getQuestionTypeByQid(@Param("qid") int qid);
}
