package notblank.surveyfarm.utility;

import notblank.surveyfarm.domain.question.entity.Option;
import notblank.surveyfarm.domain.question.entity.Question;
import notblank.surveyfarm.domain.question.entity.QuestionType;
import notblank.surveyfarm.domain.survey.dto.internal.FilterDTO;
import notblank.surveyfarm.domain.survey.dto.internal.QuestionDTO;
import notblank.surveyfarm.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.surveyfarm.domain.survey.dto.response.SurveyResponse;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.utility.CodeConverter;
import notblank.surveyfarm.domain.utility.DTOConverter;
import notblank.surveyfarm.domain.user.entity.User;
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
    private Survey survey1;
    private Survey survey2;

    private void initUser(){
        owner = User.builder()
                .nickName("messi")
                .build();
    }

    private void initSurvey1(){
        survey1 = Survey.builder()
                .owner(owner)
                .title("축구관련설문")
                .imgUrl("com")
                .regionCode(5123) // 서울(1) + 경기(2) + 대구(1024) + 부산(4096)
                .jobCode(262176) // 개발·데이터(32) + 미디어·문화·스포츠(262144)
                .ageCode(7) // 10대(1) + 20대(2) + 30대(4)
                .genderCode(3) // 남자(1)
                .build();
    }

    private void initSurvey2(){
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

        survey2 = Survey.builder()
                .owner(owner)
                .title("축구관련설문")
                .build();

        // 설문에 질문 추가해주기
        survey2.getQuestionList().add(mcQuestion1);
        survey2.getQuestionList().add(mcQuestion2);
        survey2.getQuestionList().add(saQuestion1);
    }

    @BeforeEach
    public void testSetUp(){
        codeConverter.initCodeConverter();
        initUser();
        initSurvey1();
        initSurvey2();
    }

    @Test
    @DisplayName("설문 엔티티 DTO 변경 성공 (Code 변경 성공)")
    public void getSurveyResponseDTOSuccess1() {
        // when
        SurveyResponse response = dtoConverter.toSurveyResponse(survey1);

        // then
        SurveyInfoDTO surveyInfo = response.surveyInfo();
        FilterDTO filters = response.filters();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(surveyInfo.nickName()).isEqualTo("messi");

        System.out.println(filters.regionList());
        System.out.println(filters.jobList());
        System.out.println(filters.ageList());
        System.out.println(filters.genderList());

        Assertions.assertThat(filters.regionList()).contains("서울","경기","대구","부산");
        Assertions.assertThat(filters.jobList()).hasSize(2);
        Assertions.assertThat(filters.jobList()).contains("개발·데이터", "미디어·문화·스포츠");
        Assertions.assertThat(filters.ageList()).contains("10대","20대","30대");
        Assertions.assertThat(filters.genderList()).contains("남자");
    }

    @Test
    @DisplayName("설문 엔티티 DTO 변경 성공 (Question 변경 성공)")
    public void getSurveyResponseDTOSuccess2() {
        // when
        SurveyResponse response = dtoConverter.toSurveyResponse(survey2);

        // then
        SurveyInfoDTO surveyInfo = response.surveyInfo();
        List<QuestionDTO> questions = response.questions();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(questions).hasSize(3);

        System.out.println(questions.get(0).title());
        System.out.println(questions.get(0).optionList());
        System.out.println(questions.get(1).title());
        System.out.println(questions.get(1).optionList());
        System.out.println(questions.get(2).title());
    }
}
