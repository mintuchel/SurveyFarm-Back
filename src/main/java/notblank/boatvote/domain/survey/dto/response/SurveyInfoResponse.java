package notblank.boatvote.domain.survey.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import notblank.boatvote.domain.survey.entity.Survey;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SurveyInfoResponse(
        int sid, // survey 고유 아이디
        String ownerName, // owner user 닉네임

        String title,
        String imgUrl,
        List<String> selectedRegion, // 참여가능 지역
        List<String> selectedJob, // 참여가능 직업군
        List<String> selectedGender, // 참여가능 성별
        List<String> selectedAge, // 참여가능 나이

        int maxHeadCnt, // 최대인원수
        int currentHeadCnt, // 참여인원수
        double progressRate, // 진행정도(참여인원수/총인원수)

        int point, // 포인트

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt, // 설문시작시간
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt, // 설문종료시간

        // 설문참여시간은 participatedSurvey 도메인에서만 쓰임
        // 똑같은 DTO를 participatedSurvey 쪽에서도 쓰기 위해 추가함
        // 참여가능설문을 보여줄때는 사용 안해도 되는 변수임
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime participatedAt, // 설문참여시간

        String description, // 설문 배경설명
        List<QuestionInfoResponse> questionList // 질문 List
) {
    public static SurveyInfoResponse toResponse(Survey survey, List<String> regionList, List<String> jobList, List<String> ageList, List<String> genderList) {
        return new SurveyInfoResponse(
                survey.getId(),
                survey.getOwner().getUserName(),
                survey.getTitle(),
                survey.getImgUrl(),
                regionList,
                jobList,
                ageList,
                genderList,
                survey.getMaxHeadCnt(),
                survey.getCurrentHeadCnt(),
                (double) survey.getMaxHeadCnt() / survey.getCurrentHeadCnt(),
                survey.getPoint(),
                survey.getCreatedAt(),
                survey.getEndAt(),
                null,
                survey.getDescription(),
                survey.getQuestionList().stream()
                        .map(question -> {
                            return new QuestionInfoResponse(
                                    question.getId(),
                                    question.getTitle(),
                                    question.getOptionList().stream()
                                            .map(option -> {
                                                return new OptionInfoResponse(
                                                        option.getText()
                                                );
                                            })
                                            .collect(Collectors.toList()),
                                    question.isMultipleAnswer(),
                                    question.getType()
                            );
                        })
                        .collect(Collectors.toList())
        );
    }

    // participatedAt만 업데이트해서 새로운 record 반환
    public SurveyInfoResponse updateParticipatedAt(LocalDateTime participatedAt) {
        return new SurveyInfoResponse(
                this.sid,
                this.ownerName,
                this.title,
                this.imgUrl,
                this.selectedRegion,
                this.selectedJob,
                this.selectedGender,
                this.selectedAge,
                this.maxHeadCnt,
                this.currentHeadCnt,
                this.progressRate,
                this.point,
                this.createdAt,
                this.endAt,
                participatedAt,
                this.description,
                this.questionList
        );
    }
}
