package notblank.surveyfarm.global.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import notblank.surveyfarm.global.exception.errorcode.ParticipationErrorCode;

@Getter
@AllArgsConstructor
public class ParticipationException extends RuntimeException{
    private ParticipationErrorCode errorCode;
}
