package notblank.boatvote.domain.dummy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class DummyService {
    @PersistenceContext
    private final EntityManager em;

    User user1, user2, user3;

    @Transactional
    public void initUser(){
       user1 = User.builder()
                .username("ronaldo")
                .password("7")
                .regionCode(2) // 서울
                .jobCode(1048576) // 스포츠
                .ageCode(128) // 40대
                .genderCode(1) // 남자
                .build();

       user2 = User.builder()
                .username("vini")
                .password("11")
                .regionCode(2048) // 대구
                .jobCode(64) // 개발
                .ageCode(40) // 대학생, 20대
                .genderCode(1) // 남자
                .build();

       user3 = User.builder()
                .username("mbappe")
                .password("9")
                .regionCode(131072) // 제주도
                .jobCode(16) // 회계
                .ageCode(40) // 대학생, 20대
                .genderCode(1) // 남자
                .build();

       em.persist(user1);
       em.persist(user2);
       em.persist(user3);
    }

    // 호날두만 조회가능한 Dummy Survey
    @Transactional
    public void initSurvey1() {

        // 객관식 선지 4개
        Option option1 = Option.builder().text("chelsea").build();
        Option option2 = Option.builder().text("arsenal").build();
        Option option3 = Option.builder().text("mancity").build();
        Option option4 = Option.builder().text("liverpool").build();

        // 질문 1개
        Question question = Question.builder()
                .title("응원하는 팀 고르셈")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();

        // 질문에 객관식 선지 추가해주기
        question.getOptionList().add(option1);
        question.getOptionList().add(option2);
        question.getOptionList().add(option3);
        question.getOptionList().add(option4);

        Survey survey1 = Survey.builder()
                .owner(user3)
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusDays(2))
                .regionCode(8206) // 서울(2), 경기(4), 인천(8), 부산(8192)
                .jobCode(1048640) // 개발(64) + 스포츠(1048576)
                .ageCode(160) // 20대(32) + 40대(128)
                .genderCode(1) // 남자(1)
                .headCnt(3000)
                .point(100)
                .build();

        // 설문에 질문 추가해주기
        survey1.getQuestionList().add(question);

        em.persist(survey1);
    }

    // 음바페만 조회가능한 Dummy Survey
    @Transactional
    public void initSurvey2() {

        // 객관식 선지 2개
        Option option1 = Option.builder().text("C").build();
        Option option2 = Option.builder().text("C++").build();
        Option option3 = Option.builder().text("JAVA").build();
        Option option4 = Option.builder().text("TypeScript").build();

        // 객관식 질문
        Question mcQuestion = Question.builder()
                .title("가장 좋아하는 언어는?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();

        // 객관식 질문에 객관식 선지 추가해주기
        mcQuestion.getOptionList().add(option1);
        mcQuestion.getOptionList().add(option2);
        mcQuestion.getOptionList().add(option3);
        mcQuestion.getOptionList().add(option4);

        // 주관식 질문
        Question saQuestion = Question.builder()
                .title("가장 싫어하는 언어와 그 이유를 써봐")
                .type(QuestionType.SA)
                .build();

        Survey survey2 = Survey.builder()
                .owner(user1)
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusDays(4))
                .regionCode(131072) // 제주도
                .jobCode(112) // 개발(64) + 마케팅(32) + 회계(16)
                .ageCode(48) // 20대(32) + 10대(16)
                .genderCode(1) // 남자(1)
                .headCnt(100)
                .point(30)
                .build();

        // 설문에 질문 추가해주기
        survey2.getQuestionList().add(mcQuestion);
        survey2.getQuestionList().add(saQuestion);

        em.persist(survey2);
    }
}
