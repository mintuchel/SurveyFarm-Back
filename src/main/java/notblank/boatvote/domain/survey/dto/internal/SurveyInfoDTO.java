package notblank.boatvote.domain.survey.dto.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SurveyInfoDTO(
        int sid,
        String nickName, // 유저 닉네임
        String title, // 질문 제목
        String description, // 질문 설명
        String imgUrl, // 질문 대표 이미지 URL
        int maxHeadCnt, // 목표 인원수
        int currentHeadCnt, // 참여인원수
        int duration, // 기간
        int point, // 포인트
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt, // 설문시작시간
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt // 설문종료시간
) { }