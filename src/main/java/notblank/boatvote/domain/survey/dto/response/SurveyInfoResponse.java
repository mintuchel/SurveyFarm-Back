package notblank.boatvote.domain.survey.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record SurveyInfoResponse(
        int sid, // survey 고유 아이디
        int ownerId, // owner user 고유 아이디
        List<String> selectedRegion, // 참여가능 지역
        List<String> selectedJob, // 참여가능 직업군
        List<String> selectedGender, // 참여가능 성별
        List<String> selectedAge, // 참여가능 나이
        int headCnt, // 인원수
        int point, // 포인트
        LocalDateTime createdAt, // 설문시작시간
        LocalDateTime endAt, // 설문종료시간
        // 설문참여시간은 participatedSurvey 도메인에서만 쓰임
        // 똑같은 DTO를 participatedSurvey 쪽에서도 쓰기 위해 추가함
        // 참여가능설문을 보여줄때는 사용 안해도 되는 변수임
        LocalDateTime participatedAt, // 설문참여시간
        String description, // 설문 배경설명
        List<QuestionInfoResponse> questionList // 질문 List
) {
    // participatedAt만 업데이트해서 새로운 record 반환
    public SurveyInfoResponse updateParticipatedAt(LocalDateTime participatedAt) {
        return new SurveyInfoResponse(
                this.sid,
                this.ownerId,
                this.selectedRegion,
                this.selectedJob,
                this.selectedGender,
                this.selectedAge,
                this.headCnt,
                this.point,
                this.createdAt,
                this.endAt,
                participatedAt,
                this.description,
                this.questionList
        );
    }
}
