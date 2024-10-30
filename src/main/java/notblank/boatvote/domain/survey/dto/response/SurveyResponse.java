package notblank.boatvote.domain.survey.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import notblank.boatvote.domain.survey.dto.internal.FilterDTO;
import notblank.boatvote.domain.survey.dto.internal.QuestionDTO;
import notblank.boatvote.domain.survey.dto.internal.SurveyInfoDTO;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SurveyResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime participatedAt, // 설문참여시간 (참여가능설문들을 보여줄때는 사용안해도 됨)

        SurveyInfoDTO surveyInfo,
        FilterDTO filters,
        List<QuestionDTO> questions
) {
    // participatedAt만 업데이트해서 새로운 record 반환
    public SurveyResponse updateParticipatedAt(LocalDateTime participatedAt) {
        return new SurveyResponse(
                participatedAt, // 새로운 participatedAt
                this.surveyInfo, // 기존 requiredFields
                this.filters, // 기존 filterList
                this.questions // 기존 questionList
        );
    }
}
