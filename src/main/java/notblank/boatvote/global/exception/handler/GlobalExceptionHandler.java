package notblank.boatvote.global.exception.handler;

import notblank.boatvote.global.exception.exception.ParticipationException;
import notblank.boatvote.global.exception.exception.SurveyException;
import notblank.boatvote.global.exception.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// 예외를 전역적으로 처리할 수 있는 어노테이션
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SurveyException.class)
    protected ResponseEntity<String> handleSurveyException(SurveyException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(ParticipationException.class)
    protected ResponseEntity<String> handleParticipationException(ParticipationException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(e.getErrorCode().getMessage());
    }

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<String> handleUserException(UserException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(e.getErrorCode().getMessage());
    }
}
