package notblank.surveyfarm.domain.utility;

import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.question.entity.Option;
import notblank.surveyfarm.domain.question.entity.Question;
import notblank.surveyfarm.domain.survey.dto.request.internal.SurveyFilterDTO;
import notblank.surveyfarm.domain.survey.dto.common.OptionDTO;
import notblank.surveyfarm.domain.survey.dto.common.QuestionDTO;
import notblank.surveyfarm.domain.survey.dto.request.internal.SurveyInfoDTO;
import notblank.surveyfarm.domain.survey.dto.request.CreateSurveyRequest;
import notblank.surveyfarm.domain.survey.dto.response.SurveyFilterResponse;
import notblank.surveyfarm.domain.survey.dto.response.SurveyInfoResponse;
import notblank.surveyfarm.domain.survey.dto.response.SurveyQuestionListResponse;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.survey.entity.SurveyStatus;
import notblank.surveyfarm.domain.user.dto.response.UserResponse;
import notblank.surveyfarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DTOConverter {

    private final CodeConverter codeConverter;

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .password(user.getPassword())
                .nickName(user.getNickName())
                .regionList(codeConverter.convertRegionCodeToList(user.getRegionCode()))
                .jobList(codeConverter.convertJobCodeToList(user.getJobCode()))
                .ageList(codeConverter.convertAgeCodeToList(user.getAgeCode()))
                .genderList(codeConverter.convertGenderCodeToList(user.getGenderCode()))
                .build();
    }

    public Survey toSurveyEntity(CreateSurveyRequest createSurveyRequest, User owner){

        SurveyInfoDTO surveyInfoDTO = createSurveyRequest.surveyInfo();
        SurveyFilterDTO filters = createSurveyRequest.filters();

        return Survey.builder()
                .owner(owner)
                .title(surveyInfoDTO.title())
                .description(surveyInfoDTO.description())
                .imgUrl(surveyInfoDTO.imgUrl())
                .duration(surveyInfoDTO.duration())
                .maxHeadCnt(surveyInfoDTO.maxHeadCnt())
                .currentHeadCnt(0)
                .regionCode(codeConverter.convertRegionListToRegionCode(filters.regionList()))
                .jobCode(codeConverter.convertJobListToJobCode(filters.jobList()))
                .ageCode(codeConverter.convertAgeListToAgeCode(filters.ageList()))
                .genderCode(codeConverter.convertGenderListToGenderCode(filters.genderList()))
                .point(100) // 포인트는 우리가 알아서 넣어줘야함
                .questionList(getQuestionList(createSurveyRequest.questions()))
                .surveyStatus(SurveyStatus.NEW) // 새로 만들어진 설문이므로 설문상태 "신규"로 지정
                .build();
    }

    // 병렬적으로 설문조사들을 보여줄때 사용하는 API
    // 이때는 질문들을 굳이 보여줄 필요가 없으니 카드 레이아웃에 떠야하는 최소한의 정보들만 DTO로 보내줌
    public SurveyInfoResponse toSurveyInfoResponse(Survey survey) {
        return SurveyInfoResponse.builder()
                .sid(survey.getId())
                .nickName(survey.getOwner().getNickName())
                .title(survey.getTitle())
                .description(survey.getDescription())
                .imgUrl(survey.getImgUrl())
                .maxHeadCnt(survey.getMaxHeadCnt())
                .currentHeadCnt(survey.getCurrentHeadCnt())
                .duration(survey.getDuration())
                .point(survey.getPoint())
                .createdAt(survey.getCreatedAt())
                .endAt(survey.getEndAt())
                .surveyStatus(survey.getSurveyStatus())
                .build();
    }

    public SurveyFilterResponse toSurveyFilterResponse(Survey survey){
        return SurveyFilterResponse.builder()
                .regionList(codeConverter.convertRegionCodeToList(survey.getRegionCode()))
                .jobList(codeConverter.convertJobCodeToList(survey.getJobCode()))
                .ageList(codeConverter.convertAgeCodeToList(survey.getAgeCode()))
                .genderList(codeConverter.convertGenderCodeToList(survey.getGenderCode()))
                .build();
    }

    // 특정 설문조사에 대한 참여버튼을 클릭했을 시
    // 이때는 해당 설문조사의 질문들을 보여줘야하므로 그제서야 API를 통해 QuestionList를 Response로 받으면 됨
    public SurveyQuestionListResponse toSurveyQuestionListResponse(Survey survey) {
            return SurveyQuestionListResponse.builder().questions(
                    survey.getQuestionList().stream() // 스트림 생성
                            .map(this::toQuestionDto) // Question 객체를 QuestionDTO로 변환
                            .toList())
                    .build();
    }

    private QuestionDTO toQuestionDto(Question question) {
        return QuestionDTO.builder()
                .qid(question.getId())
                .title(question.getTitle())
                .isMultipleChoice(question.isMultipleChoice())
                .optionList(question.getOptionList().stream() // 스트림 생성
                        .map(this::toOptionDto) // Option 객체를 OptionDTO로 변환
                        .toList())
                .questionType(question.getQuestionType())
                .build();
    }

    private OptionDTO toOptionDto(Option option) {
        return OptionDTO.builder()
                .text(option.getText())
                .build();
    }

    // Survey 에 집어넣을 QuestionList return
    private List<Question> getQuestionList(List<QuestionDTO> questionDTOList){
        List<Question> questionList = new ArrayList<>();
        for(QuestionDTO questionDTO : questionDTOList){
            Question curQuestion = Question.builder()
                    .title(questionDTO.title())
                    .isMultipleChoice(questionDTO.isMultipleChoice())
                    .questionType(questionDTO.questionType())
                    .build();

            curQuestion.getOptionList().addAll(getOptionList(questionDTO.optionList()));

            questionList.add(curQuestion);
        }
        return questionList;
    }

    // Question 에 집어넣을 OptionList return
    private List<Option> getOptionList(List<OptionDTO> optionDTOList){
        List<Option> optionList = new ArrayList<>();
        for (OptionDTO curOptionDTO : optionDTOList) {
            Option option = Option.builder()
                    .text(curOptionDTO.text())
                    .build();
            optionList.add(option);
        }
        return optionList;
    }
}
