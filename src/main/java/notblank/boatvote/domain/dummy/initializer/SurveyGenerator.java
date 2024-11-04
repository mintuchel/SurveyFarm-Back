package notblank.boatvote.domain.dummy.initializer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import notblank.boatvote.domain.answer.entity.Answer;
import notblank.boatvote.domain.participation.entity.Participation;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;

import java.util.List;

public abstract class SurveyGenerator {

    // 상속받은 클래스에서 사용할 수 있도록 protected 접근 제어자 사용
    @PersistenceContext
    protected EntityManager em;

    protected User findUserByNickName(String nickName) {
        String jpql = "SELECT u FROM User u WHERE u.nickName = :nickName";
        return em.createQuery(jpql, User.class)
                .setParameter("nickName", nickName)
                .getSingleResult();
    }

    protected Question createQuestion(String title, QuestionType questionType, boolean isMultipleAnswer, List<String> options){
        Question q =  Question.builder()
                .title(title)
                .type(questionType)
                .isMultipleAnswer(isMultipleAnswer)
                .build();
        options.forEach(text -> q.getOptionList().add(Option.builder().text(text).build()));

        return q;
    }

    protected void createParticipation(Survey survey, User user){
        Participation participation = Participation.builder()
                .survey(survey)
                .user(user)
                .build();

        em.persist(participation);
    }

    protected void createAnswer(Question question, User participant, String answerText){
        Answer answer = Answer.builder()
                .question(question)
                .participant(participant)
                .text(answerText)
                .build();

        em.persist(answer);
    }
}
