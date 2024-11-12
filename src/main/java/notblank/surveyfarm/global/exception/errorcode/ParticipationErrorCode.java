package notblank.surveyfarm.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ParticipationErrorCode {

    ALREADY_EXISTS(HttpStatus.ALREADY_REPORTED, "해당 설문에 이미 참여하셨습니다");

    private final HttpStatus status;
    private final String message;
}
