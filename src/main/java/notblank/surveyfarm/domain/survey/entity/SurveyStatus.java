package notblank.surveyfarm.domain.survey.entity;

import lombok.Getter;

// 자바에서의 Enum은 클래스다
@Getter
public enum SurveyStatus {
    NEW("신규"),
    IN_PROGRESS("진행중"),
    DEADLINE_UPCOMING("마감임박"),
    CLOSED("마감");

    private String description;

    SurveyStatus(String description) {
        this.description = description;
    }
}
