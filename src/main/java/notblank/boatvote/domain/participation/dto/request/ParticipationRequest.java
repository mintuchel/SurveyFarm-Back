package notblank.boatvote.domain.participation.dto.request;

public record ParticipationRequest(
    String nickName,
    int sid
) { }
