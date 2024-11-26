package notblank.surveyfarm.domain.survey.entity;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 자바에서의 Enum은 클래스다
// 자바의 Enum은 불변 객체처럼 동작하므로, Enum 인스턴스 자체의 값을 직접 변경할 수는 없다
// Enum 안에 해당 Enum 과 연관된 함수를 정의함으로써 정말 객체처럼 사용할 수 있다

@Getter
public enum SurveyStatus {
    NEW("신규"),
    IN_PROGRESS("진행중"),
    TRENDING("인기"),
    DEADLINE_UPCOMING("마감임박"),
    CLOSED("마감");

    private String description;

    SurveyStatus(String description) {
        this.description = description;
    }

    public static SurveyStatus calculateStatus(LocalDateTime createdAt, LocalDateTime endAt, double progressRate){
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(endAt)) return CLOSED;
        else if(now.toLocalDate().isEqual(createdAt.toLocalDate())) return NEW;
        else if(now.isAfter(endAt.minusDays(3))) return DEADLINE_UPCOMING;
        else if(now.isBefore(createdAt.plusDays(10)) && progressRate >= 0.3) return TRENDING;
        else return IN_PROGRESS;
    }
}
