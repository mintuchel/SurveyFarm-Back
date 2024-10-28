package notblank.boatvote.domain.dummy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.answer.entity.Answer;
import notblank.boatvote.domain.participation.entity.Participation;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class DummyService {
    @PersistenceContext
    private final EntityManager em;

    User user1, user2, user3;
    Survey survey1, survey2, survey3;

    @Transactional
    public void initUser(){
       user1 = User.builder()
                .userName("ronaldo")
                .password("7")
                .regionCode(1) // 서울
                .jobCode(262144) // 미디어·문화·스포츠
                .ageCode(8) // 40대
                .genderCode(1) // 남자
                .build();

       user2 = User.builder()
                .userName("vini")
                .password("11")
                .regionCode(1024) // 대구
                .jobCode(32) // 개발·데이터
                .ageCode(2) // 20대
                .genderCode(1) // 남자
                .build();

       user3 = User.builder()
                .userName("mbappe")
                .password("9")
                .regionCode(66536) // 제주
                .jobCode(32) // 개발·데이터
                .ageCode(2) // 20대
                .genderCode(1) // 남자
                .build();

       em.persist(user1);
       em.persist(user2);
       em.persist(user3);
    }

    // 비니시우스만 조회가능한 Dummy Survey
    @Transactional
    public void initSurvey1() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 좋아하는 프리미어리그 축구팀은 어디인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("맨시티", "리버풀", "아스날", "첼시", "토트넘", "뉴캐슬", "맨유", "브라이튼", "울버햄튼")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("가장 좋아하는 라리가 축구팀은 어디인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("FC 바르셀로나", "레알 마드리드", "아틀레티코 마드리드", "세비야","비아레얄", "마요르카")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 1
        Question saQuestion1 = Question.builder()
                .title("축구를 좋아하는 이유는 무엇인가요?")
                .type(QuestionType.SA)
                .build();

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("현재 세계 최고의 축구 선수는 누구라고 생각하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("리오넬 메시", "크리스티아누 호날두", "손흥민", "킬리안 음바페", "엘링 홀란드", "케빈 데브라이너", "황희찬")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 4
        Question mcQuestion4 = Question.builder()
                .title("메시와 호날두의 시대는 졌다고 생각하나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("네", "아니요")
                .forEach(text -> mcQuestion4.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 4
        Question mcQuestion5 = Question.builder()
                .title("평소 축구를 얼마나 자주 시청하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("거의 매 경기", "주 1-2회", "월 1-2회", "가끔만", "전혀 보지 않는다", "월드컵 등 중요한 경기만")
                .forEach(text -> mcQuestion5.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 1
        Question saQuestion2 = Question.builder()
                .title("현재 정몽규 회장과 홍명보 감독의 축구협회 논란에 대해 어떻게 생각하시나요?")
                .type(QuestionType.SA)
                .build();

        String description = "이 설문은 축구 팬들이 선호하는 팀과 선수, 시청 빈도, 그리고 축구를 좋아하는 이유를 파악하기 위해 마련되었습니다. 팬들의 관심사를 분석해 축구 관련 콘텐츠와 이벤트 기획에 참고하고자 합니다.";

        survey1 = Survey.builder()
                .owner(user3)
                .title("축구관련설문")
                .imgUrl("")
                .regionCode(5123) // 서울(1) + 경기(2) + 대구(1024) + 부산(4096)
                .jobCode(262176) // 개발·데이터(32) + 미디어·문화·스포츠(262144)
                .ageCode(7) // 10대(1) + 20대(2) + 30대(4)
                .genderCode(1) // 남자(1)
                .maxHeadCnt(3000)
                .currentHeadCnt(0)
                .point(100)
                .description(description)
                .duration(1)
                .build();

        // 설문에 질문 추가해주기
        survey1.getQuestionList().add(mcQuestion1);
        survey1.getQuestionList().add(mcQuestion2);
        survey1.getQuestionList().add(saQuestion1);
        survey1.getQuestionList().add(mcQuestion3);
        survey1.getQuestionList().add(mcQuestion4);
        survey1.getQuestionList().add(mcQuestion5);
        survey1.getQuestionList().add(saQuestion2);

        em.persist(survey1);

        initAnswer(mcQuestion1, user2, "4"); // 첼시
        initAnswer(mcQuestion2, user2, "2"); // 레알
        initAnswer(saQuestion1, user2, "재밌고 짜릿하고 흥미진진하고 치열하고 감격스럽고 박주영의 감아차기에는 감동이있어서요");
        initAnswer(mcQuestion3, user2, "3"); // 손흥민
        initAnswer(mcQuestion4, user2, "1"); // 네
        initAnswer(mcQuestion5, user2, "3"); // 월 1-2회
        initAnswer(saQuestion2, user2, "그런건 모르겠고 박주영의 제트디에는 감동이 있습니다");
    }

    // 음바페만 조회가능한 Dummy Survey
    @Transactional
    public void initSurvey2() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("현재 몇학년 이신가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("1학년", "2학년", "3학년", "4학년","휴학생")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("가장 선호하는 프로그래밍 언어는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("C", "C++", "Java", "TypeScript","Python")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("주로 사용하는 개발 도구는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(true)
                .build();
        List.of("VS Code", "IntelliJ", "PyCharm", "Eclipse","Visual Studio")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 4
        Question mcQuestion4 = Question.builder()
                .title("주로 활동하는 개발 분야는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("웹 개발", "앱 개발", "데이터 사이언스", "인프라 운영","게임 개발")
                .forEach(text -> mcQuestion4.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 1
        Question saQuestion1 = Question.builder()
                .title("개발자로서 가장 중요하게 생각하는 가치는 무엇인가요?")
                .type(QuestionType.SA)
                .build();

        String description = "이 설문은 컴공과 대학생들이 선호하는 언어, 도구, 활동 분야와 중요 가치관을 조사하여 개발 문화와 트렌드를 분석하기 위한 목적입니다. 개발자 지원 프로그램 및 학습 자료 개선에 활용할 예정입니다.";

        survey2 = Survey.builder()
                .owner(user1)
                .title("컴공과 대학생들을 위한 설문")
                .imgUrl("")
                .regionCode(65536) // 제주
                .jobCode(32) // 개발·데이터(32)
                .ageCode(3) // 10대(1) + 20대(2)
                .genderCode(1) // 남자(1)
                .maxHeadCnt(100)
                .currentHeadCnt(0)
                .point(30)
                .description(description)
                .duration(3)
                .build();

        // 설문에 질문 추가해주기
        survey2.getQuestionList().add(mcQuestion1);
        survey2.getQuestionList().add(mcQuestion2);
        survey2.getQuestionList().add(mcQuestion3);
        survey2.getQuestionList().add(mcQuestion4);
        survey2.getQuestionList().add(saQuestion1);

        em.persist(survey2);

        initAnswer(mcQuestion1, user3, "3"); // 3학년
        initAnswer(mcQuestion2, user3, "2"); // C++
        initAnswer(mcQuestion3, user3, "1"); // VS Code
        initAnswer(mcQuestion3, user3, "2"); // IntelliJ
        initAnswer(mcQuestion4, user3, "1"); // 웹개발
        initAnswer(saQuestion1, user3, "개발자로서의 가장 중요하게 생각하는 것은 박주영의 퍼터에는 감동이 있다는 것입니다");
    }

    // 모두 조회가능한 Dummy Survey
    @Transactional
    public void initSurvey3() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 선호하는 운동 종류는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("축구", "농구", "테니스", "야구", "런닝", "수영", "헬스")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("운동을 하는 주된 이유는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(true)
                .build();
        List.of("건강 관리", "체중 감량", "스트레스 해소", "체력 증가", "취미 활동")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 1
        Question saQuestion1 = Question.builder()
                .title("운동을 더 꾸준히 하기 위해 필요한 요소는 무엇인가요?")
                .type(QuestionType.SA)
                .build();

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("가장 선호하는 운동 시간대는 언제인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("새벽", "아침", "점심", "저녁", "유동적이다")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 2
        Question saQuestion2 = Question.builder()
                .title("건강 목표가 무엇이고 이를 이루기 위한 계획이 있으시면 적어주세요")
                .type(QuestionType.SA)
                .build();

        String description = "이 설문은 사람들이 선호하는 운동 유형과 운동을 하는 이유, 선호 시간대를 파악하기 위해 마련되었습니다. 이 정보를 통해 운동 관련 콘텐츠와 프로그램 기획에 활용하고자 합니다.";

        survey3 = Survey.builder()
                .owner(user3)
                .title("한국건강관리협회 설문")
                .imgUrl("")
                .regionCode(131071) // 전체
                .jobCode(8388607) // 전체
                .ageCode(30) // 20대부터 50대 이상
                .genderCode(3) // 전체
                .maxHeadCnt(2000)
                .currentHeadCnt(0)
                .point(50)
                .description(description)
                .duration(5)
                .build();

        // 설문에 질문 추가해주기
        survey3.getQuestionList().add(mcQuestion1);
        survey3.getQuestionList().add(mcQuestion2);
        survey3.getQuestionList().add(saQuestion1);
        survey3.getQuestionList().add(mcQuestion3);
        survey3.getQuestionList().add(saQuestion2);

        em.persist(survey3);

        initAnswer(mcQuestion1, user1, "3"); // 테니스
        initAnswer(mcQuestion2, user1, "3"); // 스트레스 해소
        initAnswer(mcQuestion2, user1, "5"); // 취미활동
        initAnswer(saQuestion1, user1, "몰라요 저한테 이런 어려운 질문하지 마세요");
        initAnswer(mcQuestion3, user1, "4"); // 저녁
        initAnswer(saQuestion2, user1, "제 건강 목표는 호날두 처럼 되기 입니다!");

        initAnswer(mcQuestion1, user3, "1"); // 축구
        initAnswer(mcQuestion2, user3, "1"); // 건강관리
        initAnswer(mcQuestion2, user3, "2"); // 스트레스 해소
        initAnswer(mcQuestion2, user3, "4"); // 체력 증가
        initAnswer(saQuestion1, user3, "운동을 더 꾸준히 하기 위해 필요한 것은 지구력이라고 생각합니다");
        initAnswer(mcQuestion3, user3, "1"); // 새벽
        initAnswer(saQuestion2, user3, "박주영의 감아차기에는 감동이 있다");
    }

    @Transactional
    public void initParticipatedSurvey(){
        Participation ps1 = Participation.builder()
                .survey(survey1)
                .user(user2)
                .build();

        Participation ps2 = Participation.builder()
                .survey(survey2)
                .user(user3)
                .build();

        Participation ps3 = Participation.builder()
                .survey(survey3)
                .user(user1)
                .build();

        Participation ps4 = Participation.builder()
                .survey(survey3)
                .user(user3)
                .build();

        em.persist(ps1);
        em.persist(ps2);
        em.persist(ps3);
        em.persist(ps4);
    }

    @Transactional
    public void initAnswer(Question question, User participant, String answerText){
        Answer answer = Answer.builder()
                .question(question)
                .participant(participant)
                .text(answerText)
                .build();

        em.persist(answer);
    }
}
