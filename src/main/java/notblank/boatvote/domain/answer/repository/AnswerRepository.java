package notblank.boatvote.domain.answer.repository;

import notblank.boatvote.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    // 특정 User가 특정 질문에 답한 거 보여주기

    // 객관식 질문에 대한 답변 개수 보여주기
    @Query(value = "SELECT COUNT(*) AS SUM FROM answer WHERE qid = :qid GROUP BY mcAnswer", nativeQuery = true)
    List<Integer> getMCQuestionResult(@Param("qid") int qid);

    // 주관식 질문에 대한 답변 결과 보여주기
    @Query(value = "SELECT saAnswer FROM answer WHERE qid = :qid", nativeQuery = true)
    List<String> getSAQuestionResult(@Param("qid") int qid);
}