package notblank.surveyfarm.domain.dummy.initializer;

import notblank.surveyfarm.domain.question.entity.Question;
import notblank.surveyfarm.domain.question.entity.QuestionType;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.user.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrganizationSurveyGenerator extends SurveyGenerator {

    @Transactional
    public void generateOrganizationSurveys() {
        generateKFASurvey();
        generateOSASurvey();
    }

    private void generateKFASurvey() {
        // 생성할 질문을 간단히 정의
        Question mcQuestion1 = createQuestion(
                "가장 좋아하는 프리미어리그 축구팀은 어디인가요?",
                QuestionType.MC, false,
                List.of("맨시티", "리버풀", "아스날", "첼시", "토트넘", "뉴캐슬", "맨유", "브라이튼", "울버햄튼")
        );

        Question mcQuestion2 = createQuestion(
                "가장 좋아하는 라리가 축구팀은 어디인가요?",
                QuestionType.MC, false,
                List.of("FC 바르셀로나", "레알 마드리드", "아틀레티코 마드리드", "세비야", "비아레얄", "마요르카")
        );

        Question saQuestion1 = createQuestion(
                "축구를 좋아하는 이유는 무엇인가요?",
                QuestionType.SA, false,
                List.of()
        );

        Question mcQuestion5 = createQuestion(
                "평소 축구를 얼마나 자주 시청하시나요?",
                QuestionType.MC, false,
                List.of("거의 매 경기", "주 1-2회", "월 1-2회", "가끔만", "전혀 보지 않는다", "월드컵 등 중요한 경기만")
        );


        String description = "이 설문은 축구 팬들이 선호하는 팀과 선수, 시청 빈도, 그리고 축구를 좋아하는 이유를 파악하기 위해 마련되었습니다. 팬들의 관심사를 분석해 축구 관련 콘텐츠와 이벤트 기획에 참고하고자 합니다.";

        Survey KFA_survey = Survey.builder()
                .owner(findUserByNickName("KFA")) // owner 설정
                .title("국민들의 축구 관심도 조사")
                .imgUrl("https://yachuk.com/news/photo/202002/112156_74075_4334.jpg")
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

        // 설문에 질문 추가
        KFA_survey.getQuestionList().addAll(
                List.of(mcQuestion1, mcQuestion2, saQuestion1, mcQuestion5)
        );

        em.persist(KFA_survey);

        // 참여내역 저장 + 답변 저장
        User mintuchel = findUserByNickName("mintuchel");

        createParticipation(KFA_survey, mintuchel);
        createAnswer(mcQuestion1, mintuchel, "4"); // 첼시
        createAnswer(mcQuestion2, mintuchel, "2"); // 레알
        createAnswer(saQuestion1, mintuchel, "재밌고 짜릿하고 흥미진진하고 치열하고 감격스럽고 박주영의 감아차기에는 감동이있어서요");
        createAnswer(mcQuestion5, mintuchel, "3"); // 월 1-2회
    }

    private void generateOSASurvey(){

        Question mcQuestion1 = createQuestion(
                "가장 선호하는 프로그래밍 언어는 무엇인가요?",
                QuestionType.MC, false,
                List.of("C", "C++", "Java", "TypeScript", "Python")
        );

        Question mcQuestion2 = createQuestion(
                "주로 사용하는 오픈소스 개발 도구는 무엇인가요?",
                QuestionType.MC, true,  // 다중 선택 가능
                List.of("VS Code", "IntelliJ", "PyCharm", "Eclipse", "Visual Studio")
        );

        Question mcQuestion3 = createQuestion(
                "주로 활동하는 개발 분야는 무엇인가요?",
                QuestionType.MC, false,
                List.of("웹 개발", "앱 개발", "데이터 사이언스", "인프라 운영", "게임 개발")
        );

        Question saQuestion1 = createQuestion(
                "오픈소스로 배포해본 프로그램이 있으신가요? 있으시다면 자세히 설명해주세요",
                QuestionType.SA, false, // 주관식 질문은 다중 선택이 필요 없으므로 false
                List.of() // 주관식은 옵션이 없으므로 빈 리스트
        );

        String description = "이 설문은 컴공과 대학생들이 선호하는 언어, 도구, 활동 분야와 중요 가치관을 조사하여 개발 문화와 트렌드를 분석하기 위한 목적입니다. 개발자 지원 프로그램 및 학습 자료 개선에 활용할 예정입니다.";

        Survey survey2 = Survey.builder()
                .owner(findUserByNickName("OSA"))
                .title("오픈소스 관련 설문조사")
                .imgUrl("https://as2.ftcdn.net/v2/jpg/05/03/11/11/1000_F_503111148_UAbl8AeIpTuG4uhpjkcJii9CcZR1wQNz.jpg")
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
        survey2.getQuestionList().add(saQuestion1);

        em.persist(survey2);

        // 참여내역 저장 + 답변 저장
        User huro0906 = findUserByNickName("huro0906");

        createParticipation(survey2, huro0906);
        createAnswer(mcQuestion1, huro0906, "1"); // C++
        createAnswer(mcQuestion2, huro0906, "1"); // VS Code
        createAnswer(mcQuestion2, huro0906, "2"); // IntelliJ
        createAnswer(mcQuestion3, huro0906, "4"); // 인프라 운영
        createAnswer(saQuestion1, huro0906, "없어");
    }

    private void generateMinisterySurvey(){
        Question question1 = createQuestion(
                "현재 이용하고 있는 대중교통 수단은 무엇인가요?",
                QuestionType.MC, false,
                List.of("버스", "지하철", "택시", "자전거", "도보", "기타")
        );

        Question question2 = createQuestion(
                "대중교통 이용 시 가장 중요하게 생각하는 요소는 무엇인가요?",
                QuestionType.MC, false,
                List.of("정시성", "편리성", "안전성", "비용", "청결", "서비스 품질")
        );

        Question question3 = createQuestion(
                "국토교통부에서 개선해야 한다고 생각하는 교통 정책이나 서비스는 무엇인가요?",
                QuestionType.SA, false,
                List.of() // 주관식 질문은 옵션이 필요 없음
        );

        String description = "국토교통부에서는 시민들의 대중교통 이용 패턴과 요구를 파악하여 정책 개선 및 서비스 향상에 활용하고자 합니다.";

        Survey ministryOfLandSurvey = Survey.builder()
                .owner(findUserByNickName("MOF"))
                .title("대중교통 이용 관련 설문조사")
                .imgUrl("https://blog.kakaocdn.net/dn/3JIUU/btrwpiWlCV5/kUxpxTWxv7Zv84HdzpKLD0/img.png")
                .regionCode(0)
                .jobCode(0)
                .ageCode(0)
                .genderCode(3)
                .maxHeadCnt(10000)
                .currentHeadCnt(5000)
                .point(50)
                .description(description)
                .duration(14)
                .build();

        // 설문에 질문 추가해주기
        ministryOfLandSurvey.getQuestionList().add(question1);
        ministryOfLandSurvey.getQuestionList().add(question2);
        ministryOfLandSurvey.getQuestionList().add(question3);

        em.persist(ministryOfLandSurvey);
    }
}
