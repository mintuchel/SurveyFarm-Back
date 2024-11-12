package notblank.surveyfarm.domain.participation.vo;

import java.time.LocalDateTime;

public record ParticipationInfoVO(
    int sid,
    LocalDateTime participated_at
) { }