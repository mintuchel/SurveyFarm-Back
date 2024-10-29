package notblank.boatvote.global.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import notblank.boatvote.global.exception.errorcode.SurveyErrorCode;

@Getter
@AllArgsConstructor
public class SurveyException extends RuntimeException {
    private SurveyErrorCode errorCode;
}
