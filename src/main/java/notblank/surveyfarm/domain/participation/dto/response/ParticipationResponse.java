package notblank.surveyfarm.domain.participation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ParticipationResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime participatedAt // 설문참여시간 (참여가능설문들을 보여줄때는 사용안해도 됨)
) { }
