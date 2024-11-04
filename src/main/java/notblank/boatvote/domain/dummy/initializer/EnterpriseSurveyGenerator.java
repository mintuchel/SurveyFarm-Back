package notblank.boatvote.domain.dummy.initializer;

import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.entity.Survey;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class EnterpriseSurveyGenerator extends SurveyGenerator {

    @Transactional
    public void generateEnterpriseSurveys(){
        generateNetflixSurvey();
        generateSpotifySurvey();
        generateTossSurvey();
        generateKoreanAirSurvey();
    }

    private void generateNetflixSurvey(){

        Question question1 = createQuestion(
                "최근 가장 인상 깊게 본 넷플릭스 오리지널 시리즈는 무엇인가요?",
                QuestionType.MC, false,
                List.of("오징어 게임", "종이의 집", "흑백요리사", "더글로리", "기묘한 이야기")
        );

        Question question2 = createQuestion(
                "OTT 서비스에서 영화를 얼마나 자주 시청하시나요?",
                QuestionType.MC, false,
                List.of("매주 1회 이상", "월 1-2회", "가끔", "거의 안 본다", "특정 시즌에 몰아본다")
        );

        Question question3 = createQuestion(
                "OTT 서비스 산업에 대해 어떻게 생각하시나요?",
                QuestionType.SA, false,
                List.of() // 주관식 질문은 옵션이 필요 없음
        );

        String description = "넷플릭스 한국지사에서 선호 장르, 감독, 감상 빈도 등을 파악하여 콘텐츠 기획에 참고하고자 합니다.";

        Survey netflix_survey = Survey.builder()
                .owner(findUserByNickName("Netflix_KR"))
                .title("OTT 플랫폼 관련 설문")
                .imgUrl("https://cdn.digitaltoday.co.kr/news/photo/201606/71043_86083_3646.jpg")
                .regionCode(1536)
                .jobCode(384)
                .ageCode(0)
                .genderCode(0)
                .maxHeadCnt(1000)
                .currentHeadCnt(500)
                .point(100)
                .description(description)
                .duration(10)
                .build();

        // 설문에 질문 추가해주기
        netflix_survey.getQuestionList().add(question1);
        netflix_survey.getQuestionList().add(question2);
        netflix_survey.getQuestionList().add(question3);

        em.persist(netflix_survey);
    }

    private void generateSpotifySurvey(){
        // 객관식 질문 1
        Question mcQuestion1 = Question.builder()
                .title("가장 좋아하는 음악 장르는 무엇인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("걸그룹(댄스)", "힙합", "R&B", "발라드")
                .forEach(text -> mcQuestion1.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 2
        Question mcQuestion2 = Question.builder()
                .title("가장 좋아하는 가수는 누구인가요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("NewJeans", "블랙핑크", "아이유", "르세라핌", "QWER", "프로미스나인")
                .forEach(text -> mcQuestion2.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 4
        Question mcQuestion4 = Question.builder()
                .title("음악을 주로 어디에서 감상하시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("스트리밍 앱", "유튜브", "음반 구매", "라이브 공연")
                .forEach(text -> mcQuestion4.getOptionList().add(Option.builder().text(text).build()));

        // 객관식 질문 5
        Question mcQuestion5 = Question.builder()
                .title("Spotify로 음악을 얼마나 자주 들으시나요?")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();
        List.of("매일", "주 1-2회", "가끔", "거의 안 듣는다")
                .forEach(text -> mcQuestion5.getOptionList().add(Option.builder().text(text).build()));

        String description = "이 설문은 Spotify_KR에서 의뢰한 음악 관련 설문조사입니다";

        Survey spotify_survey = Survey.builder()
                .owner(findUserByNickName("Spotify_KR"))
                .title("음악취향설문")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Spotify_logo_vertical_black.jpg/628px-Spotify_logo_vertical_black.jpg")
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
        spotify_survey.getQuestionList().add(mcQuestion1);
        spotify_survey.getQuestionList().add(mcQuestion2);
        spotify_survey.getQuestionList().add(mcQuestion4);
        spotify_survey.getQuestionList().add(mcQuestion5);

        em.persist(spotify_survey);
    }

    private void generateTossSurvey(){
        Question question1 = createQuestion(
                "토스를 사용하여 주로 어떤 서비스를 이용하시나요?",
                QuestionType.MC, false,
                List.of("송금", "신용카드 관리", "대출", "투자", "보험", "결제 서비스", "기타")
        );

        Question question2 = createQuestion(
                "토스 앱의 사용자 경험(UX)에 대해 어떻게 평가하시나요?",
                QuestionType.MC, false,
                List.of("매우 만족", "만족", "보통", "불만족", "매우 불만족")
        );

        Question question3 = createQuestion(
                "토스의 서비스 중 개선이 필요하다고 생각하는 점은 무엇인가요?",
                QuestionType.SA, false,
                List.of() // 주관식 질문은 옵션이 필요 없음
        );

        String description = "토스에서 고객의 서비스 이용 패턴, 선호 기능, 개선 사항 등을 파악하여 더 나은 금융 서비스를 제공하기 위한 기초 자료로 활용하고자 합니다.";

        Survey toss_survey = Survey.builder()
                .owner(findUserByNickName("Toss"))
                .title("토스 앱 이용관련 설문")
                .imgUrl("https://framerusercontent.com/images/EhEElRcoy4v5Y9uyUj3XkTWg.jpg")
                .regionCode(0)
                .jobCode(384)
                .ageCode(0)
                .genderCode(3)
                .maxHeadCnt(5000)
                .currentHeadCnt(2300)
                .point(50)
                .description(description)
                .duration(7)
                .build();

        // 설문에 질문 추가해주기
        toss_survey.getQuestionList().add(question1);
        toss_survey.getQuestionList().add(question2);
        toss_survey.getQuestionList().add(question3);

        em.persist(toss_survey);
    }

    private void generateKoreanAirSurvey(){
        Question question1 = createQuestion(
                "대한항공을 이용한 이유는 무엇인가요?",
                QuestionType.MC, false,
                List.of("항공편의 가격", "항공편의 시간", "항공사 서비스", "직항 노선", "마일리지 적립", "기타")
        );

        Question question2 = createQuestion(
                "대한항공의 서비스에 대한 전반적인 만족도는 어떠신가요?",
                QuestionType.MC, false,
                List.of("매우 만족", "만족", "보통", "불만족", "매우 불만족")
        );

        Question question3 = createQuestion(
                "대한항공에서 개선이 필요하다고 생각하는 서비스는 무엇인가요?",
                QuestionType.SA, false,
                List.of() // 주관식 질문은 옵션이 필요 없음
        );

        String description = "대한항공에서 고객의 여행 경험, 서비스 만족도 및 개선 사항을 파악하여 더 나은 항공 서비스를 제공하기 위한 기초 자료로 활용하고자 합니다.";

        Survey koreanAir_survey = Survey.builder()
                .owner(findUserByNickName("KoreanAir"))
                .title("대한항공 서비스 이용관련 설문")
                .imgUrl("https://blog.kakaocdn.net/dn/w9qx5/btsH6ytpwCX/1WwAkkvMeMgYRRe6iDtsUK/img.jpg") // 적절한 이미지 URL로 변경
                .regionCode(0)
                .jobCode(0)
                .ageCode(0)
                .genderCode(3)
                .maxHeadCnt(5000)
                .currentHeadCnt(2300)
                .point(50)
                .description(description)
                .duration(7)
                .build();

// 설문에 질문 추가해주기
        koreanAir_survey.getQuestionList().add(question1);
        koreanAir_survey.getQuestionList().add(question2);
        koreanAir_survey.getQuestionList().add(question3);

        em.persist(koreanAir_survey);

    }
}
