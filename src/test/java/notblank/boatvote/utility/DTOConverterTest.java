package notblank.boatvote.utility;

import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.dto.internal.FilterDTO;
import notblank.boatvote.domain.survey.dto.internal.QuestionDTO;
import notblank.boatvote.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.utility.CodeConverter;
import notblank.boatvote.domain.utility.DTOConverter;
import notblank.boatvote.domain.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DTOConverterTest {
    @InjectMocks
    private DTOConverter dtoConverter;

    @Spy
    private CodeConverter codeConverter;

    private User owner;
    private Survey survey;

    @BeforeEach
    public void testSetUp(){
        codeConverter.initCodeConverter();

        owner = User.builder()
                .nickName("messi")
                .password("10")
                .regionCode(1) // 서울
                .jobCode(262144) // 미디어·문화·스포츠
                .ageCode(8) // 40대
                .genderCode(1) // 남자
                .build();

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

        survey = Survey.builder()
                .owner(owner)
                .title("축구관련설문")
                .imgUrl("축구설문 imgUrl")
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
        survey.getQuestionList().add(mcQuestion1);
        survey.getQuestionList().add(mcQuestion2);
        survey.getQuestionList().add(saQuestion1);
        survey.getQuestionList().add(mcQuestion3);
        survey.getQuestionList().add(mcQuestion4);
        survey.getQuestionList().add(mcQuestion5);
        survey.getQuestionList().add(saQuestion2);
    }

    @Test
    @DisplayName("설문 엔티티 DTO 변경 성공 (entity to responseDTO 성공)")
    public void getSurveyResponseDTOSuccess() {
        // when
        SurveyResponse response = dtoConverter.toGetSurveyResponse(survey);

        // then
        SurveyInfoDTO surveyInfo = response.surveyInfo();
        FilterDTO filters = response.filters();
        List<QuestionDTO> questions = response.questions();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(surveyInfo.nickName()).isEqualTo("messi");

        Assertions.assertThat(filters.jobList()).hasSize(2);
        Assertions.assertThat(filters.regionList()).contains("서울","경기","대구","부산");
        Assertions.assertThat(filters.jobList()).contains("개발·데이터", "미디어·문화·스포츠");
        Assertions.assertThat(filters.ageList()).contains("10대","20대","30대");
        Assertions.assertThat(filters.genderList()).contains("남자");

        Assertions.assertThat(questions).hasSize(7);
    }
}
