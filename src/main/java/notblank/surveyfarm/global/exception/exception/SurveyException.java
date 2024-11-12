package notblank.surveyfarm.global.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import notblank.surveyfarm.global.exception.errorcode.SurveyErrorCode;

@Getter
@AllArgsConstructor
public class SurveyException extends RuntimeException {
    private SurveyErrorCode errorCode;
}
