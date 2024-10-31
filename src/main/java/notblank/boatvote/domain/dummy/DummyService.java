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

import java.util.List;

@Service
@RequiredArgsConstructor
class DummyService {
    @PersistenceContext
    private final EntityManager em;

    User user1, user2, user3;
    Survey survey1, survey2, survey3, survey4, survey5, survey6, survey7, survey8, survey9, survey10;

    @Transactional
    public void initUser(){
       user1 = User.builder()
                .nickName("christiano ronaldo")
                .password("7")
                .regionCode(1) // 서울
                .jobCode(262144) // 미디어·문화·스포츠
                .ageCode(8) // 40대
                .genderCode(1) // 남자
                .build();

       user2 = User.builder()
                .nickName("vinicius jr")
                .password("11")
                .regionCode(1024) // 대구
                .jobCode(32) // 개발·데이터
                .ageCode(2) // 20대
                .genderCode(1) // 남자
                .build();

       user3 = User.builder()
                .nickName("kylian mbappe")
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
                .owner(user1)
                .title("축구관련설문")
                .imgUrl("https://siri.or.kr/wp/wp-content/uploads/2022/05/epl.png")
                .regionCode(5123) // 서울(1) + 경기(2) + 대구(1024) + 부산(4096)
                .jobCode(262176) // 개발·데이터(32) + 미디어·문화·스포츠(262144)
                .ageCode(7) // 10대(1) + 20대(2) + 30대(4)
                .genderCode(1) // 남자(1)
                .maxHeadCnt(3000)
                .currentHeadCnt(1500)
                .point(100)
                .description(description)
                .duration(5)
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
                .imgUrl("https://mir-s3-cdn-cf.behance.net/project_modules/1400/3393f738210507.575900b317fb4.png")
                .regionCode(65536) // 제주
                .jobCode(32) // 개발·데이터(32)
                .ageCode(3) // 10대(1) + 20대(2)
                .genderCode(1) // 남자(1)
                .maxHeadCnt(100)
                .currentHeadCnt(25)
                .point(300)
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

        initAnswer(mcQuestion1, user2, "3"); // 3학년
        initAnswer(mcQuestion2, user2, "2"); // C++
        initAnswer(mcQuestion3, user2, "1"); // VS Code
        initAnswer(mcQuestion3, user2, "2"); // IntelliJ
        initAnswer(mcQuestion4, user2, "1"); // 웹개발
        initAnswer(saQuestion1, user2, "개발자로서의 가장 중요하게 생각하는 것은 박주영의 퍼터에는 감동이 있다는 것입니다");
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
                .imgUrl("https://cdn-icons-png.flaticon.com/512/2382/2382533.png")
                .regionCode(131071) // 전체
                .jobCode(8388607) // 전체
                .ageCode(30) // 20대부터 50대 이상
                .genderCode(3) // 전체
                .maxHeadCnt(2000)
                .currentHeadCnt(1200)
                .point(500)
                .description(description)
                .duration(20)
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

        initAnswer(mcQuestion1, user2, "1"); // 축구
        initAnswer(mcQuestion2, user2, "1"); // 건강관리
        initAnswer(mcQuestion2, user2, "2"); // 스트레스 해소
        initAnswer(mcQuestion2, user2, "4"); // 체력 증가
        initAnswer(saQuestion1, user2, "운동을 더 꾸준히 하기 위해 필요한 것은 지구력이라고 생각합니다");
        initAnswer(mcQuestion3, user2, "1"); // 새벽
        initAnswer(saQuestion2, user2, "박주영의 감아차기에는 감동이 있다");
    }

    // initSurvey4: 영화 관련 설문
    @Transactional
    public void initSurvey4() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 좋아하는 영화 장르는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("액션", "코미디", "드라마", "SF", "호러", "판타지", "애니메이션", "로맨스", "다큐멘터리")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("가장 좋아하는 영화 감독은 누구인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("크리스토퍼 놀란", "스티븐 스필버그", "쿠엔틴 타란티노", "마틴 스콜세지", "봉준호", "박찬욱")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 1
        Question saQuestion1 = Question.builder()
                .title("영화를 좋아하는 이유는 무엇인가요?")
                .type(QuestionType.SA)
                .build();

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("최근에 본 영화 중 최고라고 생각하는 영화는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("테넷", "기생충", "어벤져스", "인셉션", "인터스텔라", "올드보이", "라라랜드")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 4
        Question mcQuestion4 = Question.builder()
                .title("영화는 혼자 보는 것을 선호하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("네", "아니요")
                .forEach(text -> mcQuestion4.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 5
        Question mcQuestion5 = Question.builder()
                .title("영화를 얼마나 자주 시청하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("매주 1회 이상", "월 1-2회", "가끔", "거의 안 본다", "연말에 몰아본다")
                .forEach(text -> mcQuestion5.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 2
        Question saQuestion2 = Question.builder()
                .title("한국 영화산업에 대해 어떻게 생각하시나요?")
                .type(QuestionType.SA)
                .build();

        String description = "이 설문은 영화 팬들의 선호 장르, 감독, 감상 빈도 등을 파악하여 영화 콘텐츠 기획에 참고하고자 합니다.";

        survey4 = Survey.builder()
                .owner(user1)
                .title("영화관련설문")
                .imgUrl("https://www.brandb.net/_next/image?url=https%3A%2F%2Fapi.brandb.net%2Fapi%2Fv2%2Fcommon%2Fimage%2F%3FfileId%3D1167&w=1920&q=75")
                .regionCode(1536)
                .jobCode(384)
                .ageCode(31)
                .genderCode(3)
                .maxHeadCnt(3000)
                .currentHeadCnt(300)
                .point(350)
                .description(description)
                .duration(10)
                .build();

        // 설문에 질문 추가해주기
        survey4.getQuestionList().add(mcQuestion1);
        survey4.getQuestionList().add(mcQuestion2);
        survey4.getQuestionList().add(saQuestion1);
        survey4.getQuestionList().add(mcQuestion3);
        survey4.getQuestionList().add(mcQuestion4);
        survey4.getQuestionList().add(mcQuestion5);
        survey4.getQuestionList().add(saQuestion2);

        em.persist(survey4);
    }

    // initSurvey5: 음악 취향 설문
    @Transactional
    public void initSurvey5() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 좋아하는 음악 장르는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("팝", "락", "힙합", "R&B", "재즈", "클래식", "K-pop", "EDM")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("가장 좋아하는 가수는 누구인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("BTS", "블랙핑크", "아이유", "에드 시런", "테일러 스위프트", "드레이크", "방탄소년단")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 1
        Question saQuestion1 = Question.builder()
                .title("음악을 좋아하는 이유는 무엇인가요?")
                .type(QuestionType.SA)
                .build();

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("가장 최근에 구매한 앨범이 있다면 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("BTS - Dynamite", "블랙핑크 - THE ALBUM", "아이유 - LILAC", "에드 시런 - Equals", "테일러 스위프트 - Folklore")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 4
        Question mcQuestion4 = Question.builder()
                .title("음악을 주로 어디에서 감상하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("스트리밍 앱", "유튜브", "라디오", "음반 구매", "라이브 공연")
                .forEach(text -> mcQuestion4.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 5
        Question mcQuestion5 = Question.builder()
                .title("음악을 얼마나 자주 들으시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("매일", "주 1-2회", "가끔", "거의 안 듣는다", "기분 좋을 때")
                .forEach(text -> mcQuestion5.getOptionList().add(Option.builder().text(text).build()));

        // 주관식 질문 2
        Question saQuestion2 = Question.builder()
                .title("한국 음악산업에 대해 어떻게 생각하시나요?")
                .type(QuestionType.SA)
                .build();

        String description = "이 설문은 음악 팬들의 취향, 선호 아티스트, 감상 빈도 등을 파악하여 음악 콘텐츠 기획에 참고하고자 합니다.";

        survey5 = Survey.builder()
                .owner(user3)
                .title("음악취향설문")
                .imgUrl("https://flexible.img.hani.co.kr/flexible/normal/810/969/imgdb/child/2024/0923/17270723691377_20240923501894.jpg")
                .regionCode(131071)
                .jobCode(8388607)
                .ageCode(7)
                .genderCode(1)
                .maxHeadCnt(3000)
                .currentHeadCnt(1000)
                .point(300)
                .description(description)
                .duration(3)
                .build();

        // 설문에 질문 추가해주기
        survey5.getQuestionList().add(mcQuestion1);
        survey5.getQuestionList().add(mcQuestion2);
        survey5.getQuestionList().add(saQuestion1);
        survey5.getQuestionList().add(mcQuestion3);
        survey5.getQuestionList().add(mcQuestion4);
        survey5.getQuestionList().add(mcQuestion5);
        survey5.getQuestionList().add(saQuestion2);

        em.persist(survey5);
    }

    // initSurvey6: 영화 관련 설문
    @Transactional
    public void initSurvey6() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 좋아하는 음식 종류는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("한식", "중식", "일식", "양식", "동남아 음식", "인도 음식")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("평소 가장 선호하는 디저트는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("아이스크림", "케이크", "과일", "초콜릿", "빵", "쿠키", "기타")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("외식할 때 자주 선택하는 음식점은 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("패스트푸드", "고깃집", "일식집", "중식당", "카페", "분식점")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        String description = "이 설문은 개인의 음식 취향과 선호하는 외식 스타일을 파악하여 음식 관련 콘텐츠와 이벤트 기획에 참고하고자 합니다.";

        survey6 = Survey.builder()
                .owner(user3)
                .title("음식선호도설문")
                .imgUrl("https://www.harpersbazaar.co.kr/resources/online/online_image/2024/09/23/517a4017-c356-4f51-8678-a2a0e0550926.jpg")
                .regionCode(1)
                .jobCode(8388607)
                .ageCode(31)
                .genderCode(3)
                .maxHeadCnt(500)
                .currentHeadCnt(350)
                .point(50)
                .description(description)
                .duration(3)
                .build();

        // 설문에 질문 추가해주기
        survey6.getQuestionList().add(mcQuestion1);
        survey6.getQuestionList().add(mcQuestion2);
        survey6.getQuestionList().add(mcQuestion3);

        em.persist(survey6);
    }

    // initSurvey7: 독서 습관 설문
    @Transactional
    public void initSurvey7() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 자주 읽는 책의 장르는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("소설", "자기계발서", "과학", "역사", "심리학", "경제", "기타")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("평소 책을 주로 어디에서 읽으시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("도서관", "집", "카페", "대중교통", "공원", "기타")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("한 달에 몇 권의 책을 읽으시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("0-1권", "2-3권", "4-5권", "6권 이상")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        String description = "이 설문은 개인의 독서 습관과 선호하는 책 장르를 파악하여 독서 관련 콘텐츠 기획에 참고하고자 합니다.";

        survey7 = Survey.builder()
                .owner(user1)
                .title("독서습관설문")
                .imgUrl("https://yt3.googleusercontent.com/ytc/AIdro_mfQ2iNG47zNrcxMLjWo6RlMxI67nkuvgH53W0zPaEPzw=s900-c-k-c0x00ffffff-no-rj")
                .regionCode(127)
                .jobCode(262176)
                .ageCode(7)
                .genderCode(3)
                .maxHeadCnt(3000)
                .currentHeadCnt(1500)
                .point(250)
                .description(description)
                .duration(30)
                .build();

        // 설문에 질문 추가해주기
        survey7.getQuestionList().add(mcQuestion1);
        survey7.getQuestionList().add(mcQuestion2);
        survey7.getQuestionList().add(mcQuestion3);

        em.persist(survey7);
    }

    // initSurvey8: 여행 설문
    @Transactional
    public void initSurvey8() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 가고 싶은 여행지는 어디인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("유럽", "아시아", "북미", "남미", "아프리카", "오세아니아")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("여행 시 주로 어디에 머무르시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("호텔", "호스텔", "에어비앤비", "친구나 가족 집", "캠핑")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("여행을 얼마나 자주 가시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("연 1회 이상", "연 2-3회", "가끔", "거의 안 간다", "국내만 간다")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        String description = "이 설문은 여행 선호지와 스타일을 파악하여 여행 콘텐츠와 프로모션 기획에 참고하고자 합니다.";

        survey8 = Survey.builder()
                .owner(user2)
                .title("여행관련설문")
                .imgUrl("https://yd-donga.com/data/file/intern/3530788055_zAuvE7hg_b9ec4e6c81bc4e0840854cb37415cb20e1181f03.png")
                .regionCode(2047)
                .jobCode(131071)
                .ageCode(7)
                .genderCode(3)
                .maxHeadCnt(3000)
                .currentHeadCnt(1500)
                .point(100)
                .description(description)
                .duration(1)
                .build();

        // 설문에 질문 추가해주기
        survey8.getQuestionList().add(mcQuestion1);
        survey8.getQuestionList().add(mcQuestion2);
        survey8.getQuestionList().add(mcQuestion3);

        em.persist(survey8);
    }

    // initSurvey9: 운동 습관 설문
    @Transactional
    public void initSurvey9() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 좋아하는 운동은 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("축구", "농구", "야구", "수영", "조깅", "헬스", "요가", "테니스")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("운동을 어디에서 주로 하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("헬스장", "야외", "집", "체육관", "수영장")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("운동을 얼마나 자주 하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("주 3회 이상", "주 1-2회", "가끔", "거의 안 한다", "계절별로")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        String description = "이 설문은 운동 습관과 선호 활동을 파악하여 피트니스 관련 콘텐츠와 서비스 기획에 참고하고자 합니다.";

        survey9 = Survey.builder()
                .owner(user3)
                .title("운동습관설문")
                .imgUrl("https://mblogthumb-phinf.pstatic.net/20160728_298/ppanppane_1469696985810nqQuL_PNG/%B3%AA%C0%CC%C5%B0_%B7%CE%B0%ED_%281%29.png?type=w800")
                .regionCode(32767)
                .jobCode(96)
                .ageCode(18)
                .genderCode(2)
                .maxHeadCnt(1000)
                .currentHeadCnt(800)
                .point(150)
                .description(description)
                .duration(15)
                .build();

        // 설문에 질문 추가해주기
        survey9.getQuestionList().add(mcQuestion1);
        survey9.getQuestionList().add(mcQuestion2);
        survey9.getQuestionList().add(mcQuestion3);

        em.persist(survey9);
    }

    // initSurvey10: 반려동물 관련 설문
    @Transactional
    public void initSurvey10() {

        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 좋아하는 반려동물은 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("강아지", "고양이", "토끼", "햄스터", "조류", "물고기")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("반려동물과 주로 무엇을 하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("산책", "놀이", "훈련", "목욕", "방치", "교감")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 3
        Question mcQuestion3 = Question.builder()
                .title("반려동물에게 주로 어떤 음식을 주시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("건사료", "습식사료", "수제 음식", "간식 위주", "균형 잡힌 식단", "동물 병원 추천식")
                .forEach(text -> mcQuestion3.getOptionList().add(Option.builder().text(text).build()));

        String description = "이 설문은 반려동물에 대한 선호와 관심사를 파악하여 반려동물 관련 콘텐츠와 서비스 기획에 참고하고자 합니다.";

        survey10 = Survey.builder()
                .owner(user1)
                .title("반려동물설문")
                .imgUrl("https://image.ytn.co.kr/general/jpg/2017/1018/201710181100063682_d.jpg")
                .regionCode(1023)
                .jobCode(65535)
                .ageCode(24)
                .genderCode(3)
                .maxHeadCnt(500)
                .currentHeadCnt(100)
                .point(80)
                .description(description)
                .duration(30)
                .build();

        // 설문에 질문 추가해주기
        survey10.getQuestionList().add(mcQuestion1);
        survey10.getQuestionList().add(mcQuestion2);
        survey10.getQuestionList().add(mcQuestion3);

        em.persist(survey10);
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
