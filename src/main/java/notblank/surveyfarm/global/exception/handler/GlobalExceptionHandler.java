package notblank.surveyfarm.global.exception.handler;

import notblank.surveyfarm.global.exception.exception.ParticipationException;
import notblank.surveyfarm.global.exception.exception.SurveyException;
import notblank.surveyfarm.global.exception.exception.UserException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleGlobalException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("예기치 않은 오류가 발생했습니다. 관리자에게 문의하세요.");
    }
}
