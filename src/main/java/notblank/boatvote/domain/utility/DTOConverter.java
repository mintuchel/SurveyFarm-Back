package notblank.boatvote.domain.utility;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.survey.dto.internal.FilterDTO;
import notblank.boatvote.domain.survey.dto.internal.OptionDTO;
import notblank.boatvote.domain.survey.dto.internal.QuestionDTO;
import notblank.boatvote.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.boatvote.domain.survey.dto.request.CreateSurveyRequest;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DTOConverter {

    private final CodeConverter codeConverter;

    public Survey toSurveyEntity(CreateSurveyRequest createSurveyRequest, User owner){

        SurveyInfoDTO surveyInfoDTO = createSurveyRequest.surveyInfo();
        FilterDTO filters = createSurveyRequest.filters();

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
                .build();
    }

    public SurveyResponse toGetSurveyResponse(Survey survey) {
        return SurveyResponse.builder()
                .surveyInfo(toSurveyDTO(survey))
                .filters(toFilterListDTO(survey))
                .questions(survey.getQuestionList().stream() // 스트림 생성
                        .map(this::toQuestionDto) // Option 객체를 OptionDTO로 변환
                        .toList())
                .build();
    }

    public FilterDTO toFilterListDTO(Survey survey) {
        return FilterDTO.builder()
                .regionList(codeConverter.convertRegionCodeToList(survey.getRegionCode()))
                .jobList(codeConverter.convertJobCodeToList(survey.getJobCode()))
                .ageList(codeConverter.convertAgeCodeToList(survey.getAgeCode()))
                .genderList(codeConverter.convertGenderCodeToList(survey.getGenderCode()))
                .build();
    }

    public SurveyInfoDTO toSurveyDTO(Survey survey) {
        return SurveyInfoDTO.builder()
                .sid(survey.getId())
                .uid(survey.getOwner().getId())
                .nickName(survey.getOwner().getNickName())
                .title(survey.getTitle())
                .description(survey.getDescription())
                .imgUrl(survey.getImgUrl())
                .duration(survey.getDuration())
                .maxHeadCnt(survey.getMaxHeadCnt())
                .currentHeadCnt(survey.getCurrentHeadCnt())
                .point(survey.getPoint())
                .createdAt(survey.getCreatedAt())
                .endAt(survey.getEndAt())
                .build();
    }

    public QuestionDTO toQuestionDto(Question question) {
        return QuestionDTO.builder()
                .qid(question.getId())
                .title(question.getTitle())
                .isMultipleAnswer(question.isMultipleAnswer())
                .optionList(question.getOptionList().stream() // 스트림 생성
                        .map(this::toOptionDto) // Option 객체를 OptionDTO로 변환
                        .toList())
                .questionType(question.getType())
                .build();
    }

    public OptionDTO toOptionDto(Option option) {
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
                    .isMultipleAnswer(questionDTO.isMultipleAnswer())
                    .type(questionDTO.questionType())
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
