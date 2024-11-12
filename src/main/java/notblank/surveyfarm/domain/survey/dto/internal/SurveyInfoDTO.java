package notblank.surveyfarm.domain.survey.dto.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SurveyInfoDTO(
        int sid,
        @NotBlank int uid,
        String nickName, // 유저 닉네임
        @NotBlank String title, // 질문 제목
        @NotBlank String description, // 질문 설명
        @NotBlank String imgUrl, // 질문 대표 이미지 URL
        @NotBlank @Size(min = 100) int maxHeadCnt, // 목표 인원수
        int currentHeadCnt, // 참여인원수
        @NotBlank @Size(min = 1) int duration, // 기간
        int point, // 포인트
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt, // 설문시작시간
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt // 설문종료시간
) { }