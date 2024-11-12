package notblank.surveyfarm.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SurveyErrorCode {

    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 질문을 찾을 수 없습니다"),
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 설문을 찾을 수 없습니다");

    private final HttpStatus status;
    private final String message;
}
