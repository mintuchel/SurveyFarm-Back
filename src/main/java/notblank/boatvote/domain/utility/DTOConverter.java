package notblank.boatvote.domain.utility;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.survey.dto.internal.FilterDTO;
import notblank.boatvote.domain.survey.dto.internal.OptionDTO;
import notblank.boatvote.domain.survey.dto.internal.QuestionDTO;
import notblank.boatvote.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DTOConverter {

    private final CodeConverter codeConverter;

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
}
