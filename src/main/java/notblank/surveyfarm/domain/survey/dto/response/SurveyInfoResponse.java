package notblank.surveyfarm.domain.survey.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import notblank.surveyfarm.domain.survey.entity.SurveyStatus;

import java.time.LocalDateTime;

@Builder
public record SurveyInfoResponse(
        @NotBlank int sid,
        @NotBlank  String nickName, // 유저 닉네임
        @NotBlank String title, // 질문 제목
        @NotBlank String description, // 질문 설명
        @NotBlank String imgUrl, // 질문 대표 이미지 URL
        @NotBlank int maxHeadCnt, // 목표 인원수
        @NotBlank int currentHeadCnt, // 현재참여인원수
        @NotBlank @Size(min = 1) int duration, // 기간
        @NotBlank int point, // 포인트

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt, // 설문시작시간
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt, // 설문종료시간

        @NotBlank SurveyStatus surveyStatus // 설문상태
) { }
