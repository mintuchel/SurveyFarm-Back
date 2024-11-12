package notblank.surveyfarm.global.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import notblank.surveyfarm.global.exception.errorcode.UserErrorCode;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private UserErrorCode errorCode;
}
