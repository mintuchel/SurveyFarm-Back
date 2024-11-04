package notblank.boatvote.domain.dummy.initializer;

import jakarta.transaction.Transactional;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonalSurveyGenerator extends SurveyGenerator {

    @Transactional
    public void generatePersonalSurveys() {
        // Survey 생성 로직 추가
        generateMintuchelSurvey();
        generateHuro0906Survey();
        generateShymirrSurvey();
    }

    private void generateMintuchelSurvey() {

        Question q1 = createQuestion(
                "현재 세계 최고의 축구 선수는 누구라고 생각하시나요?",
                QuestionType.MC, false,
                List.of("리오넬 메시", "크리스티아누 호날두", "손흥민", "킬리안 음바페", "엘링 홀란드", "케빈 데브라이너", "황희찬")
        );

        Question q2 = createQuestion(
                "메시와 호날두의 시대는 졌다고 생각하나요?",
                QuestionType.MC, false,
                List.of("네", "아니요")
        );

        String description = "누가 더 GOAT라고 생각하시나요";

        Survey mintuchel_survey = Survey.builder()
                .owner(findUserByNickName("mintuchel")) // owner 설정
                .title("메시 vs 호날두")
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

        // 설문에 질문 추가
        mintuchel_survey.getQuestionList().addAll(
                List.of(q1, q2)
        );

        em.persist(mintuchel_survey);
    }

    private void generateHuro0906Survey(){

        Question mcQuestion1 = createQuestion(
                "가장 좋아하는 운동은 무엇인가요?",
                QuestionType.MC, false,
                List.of("축구", "농구", "야구", "수영", "조깅", "헬스", "요가", "테니스")
        );

        Question mcQuestion2 = createQuestion(
                "운동을 어디에서 주로 하시나요?",
                QuestionType.MC, false,
                List.of("헬스장", "야외", "집", "체육관", "수영장")
        );

        Question mcQuestion3 = createQuestion(
                "운동을 얼마나 자주 하시나요?",
                QuestionType.MC, false,
                List.of("주 3회 이상", "주 1-2회", "가끔", "거의 안 한다", "계절별로")
        );

        String description = "이 설문은 운동 습관과 선호 활동을 파악하여 피트니스 관련 콘텐츠와 서비스 기획에 참고하고자 합니다.";

        Survey huro0906_survey = Survey.builder()
                .owner(findUserByNickName("huro0906"))
                .title("운동습관설문")
                .imgUrl("https://cdn-icons-png.flaticon.com/512/4729/4729230.png")
                .regionCode(63)
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
        huro0906_survey.getQuestionList().add(mcQuestion1);
        huro0906_survey.getQuestionList().add(mcQuestion2);
        huro0906_survey.getQuestionList().add(mcQuestion3);

        em.persist(huro0906_survey);
    }

    private void generateShymirrSurvey(){
        Question mcQuestion1 = createQuestion(
                "가장 좋아하는 반려동물은 무엇인가요?",
                QuestionType.MC, false,
                List.of("강아지", "고양이", "토끼", "햄스터", "조류", "물고기")
        );

        Question mcQuestion2 = createQuestion(
                "반려동물과 주로 무엇을 하시나요?",
                QuestionType.MC, false,
                List.of("산책", "놀이", "훈련", "목욕", "방치", "교감")
        );

        Question mcQuestion3 = createQuestion(
                "반려동물에게 주로 어떤 음식을 주시나요?",
                QuestionType.MC, false,
                List.of("건사료", "습식사료", "수제 음식", "간식 위주", "균형 잡힌 식단", "동물 병원 추천식")
        );

        String description = "이 설문은 반려동물에 대한 선호와 관심사를 파악하여 반려동물 관련 콘텐츠와 서비스 기획에 참고하고자 합니다.";

        Survey shymirr_survey = Survey.builder()
                .owner(findUserByNickName("shymirr"))
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
        shymirr_survey.getQuestionList().add(mcQuestion1);
        shymirr_survey.getQuestionList().add(mcQuestion2);
        shymirr_survey.getQuestionList().add(mcQuestion3);

        em.persist(shymirr_survey);
    }
}
