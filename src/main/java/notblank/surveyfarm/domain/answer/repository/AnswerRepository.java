package notblank.surveyfarm.domain.answer.repository;

import notblank.surveyfarm.domain.answer.entity.Answer;
import notblank.surveyfarm.domain.answer.vo.MCResultVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    // 특정 User가 특정 질문에 답한 거 보여주기
    @Query(value = "SELECT * FROM answer WHERE uid = :uid AND qid = :qid", nativeQuery = true)
    List<Answer> findAnswersByUidAndQid(@Param("uid") int uid, @Param("qid") int qid);

    // 객관식 질문에 대한 답변 개수 보여주기
    @Query("SELECT new notblank.surveyfarm.domain.answer.vo.MCResultVO(a.text, COUNT(a)) FROM Answer a WHERE a.question.id = :qid GROUP BY a.text")
    List<MCResultVO> getMCQuestionResult(@Param("qid") int qid);

    // 주관식 질문에 대한 답변 결과 보여주기
    @Query(value = "SELECT text FROM answer WHERE qid = :qid", nativeQuery = true)
    List<String> getSAQuestionResult(@Param("qid") int qid);
}