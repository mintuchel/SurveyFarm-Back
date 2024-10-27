package notblank.boatvote.domain.participatedSurvey.dto.response;

import java.time.LocalDateTime;

public record ParticipatedSurveyDTO(
    int sid,
    LocalDateTime participated_at
) { }
