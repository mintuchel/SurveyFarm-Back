package notblank.surveyfarm.domain.survey.dto.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import notblank.surveyfarm.domain.survey.entity.SurveyStatus;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDateTime;

// Request 와 Response 때 모두 이 내부 DTO 사용
// @NotBlank로 선언 안된 것들은 Request 시 Client가 보낼필요가 없는 것들이어서 그럼
// @NotBlank로 선언 안된 것들은 Response 때만 사용하는거임
@Builder
public record SurveyInfoDTO(
        int sid,
        @NotBlank int uid,
        String nickName, // 유저 닉네임
        @NotBlank String title, // 질문 제목
        @NotBlank String description, // 질문 설명
        @NotBlank String imgUrl, // 질문 대표 이미지 URL
        @NotBlank @Size(min = 100) int maxHeadCnt, // 목표 인원수
        int currentHeadCnt, // 현재참여인원수
        @NotBlank @Size(min = 1) int duration, // 기간
        int point, // 포인트
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt, // 설문시작시간
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt, // 설문종료시간
        SurveyStatus surveyStatus
) { }