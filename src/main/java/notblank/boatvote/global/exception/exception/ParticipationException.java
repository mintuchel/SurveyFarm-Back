package notblank.boatvote.global.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import notblank.boatvote.global.exception.errorcode.ParticipationErrorCode;

@Getter
@AllArgsConstructor
public class ParticipationException extends RuntimeException{
    private ParticipationErrorCode errorCode;
}
