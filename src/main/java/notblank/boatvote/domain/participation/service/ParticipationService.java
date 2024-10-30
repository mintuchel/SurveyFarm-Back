package notblank.boatvote.domain.participation.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.participation.dto.request.ParticipationRequest;
import notblank.boatvote.domain.participation.entity.Participation;
import notblank.boatvote.domain.participation.repository.ParticipationRepository;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.service.UserService;
import notblank.boatvote.global.exception.errorcode.ParticipationErrorCode;
import notblank.boatvote.global.exception.exception.ParticipationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final SurveyService surveyService;
    private final UserService userService;

    private final ParticipationRepository participationRepository;

    @Transactional
    public String addNewParticipation(ParticipationRequest participationRequest) {

        int uid = participationRequest.uid();
        int sid = participationRequest.sid();

        // 이미 참여했으면 예외처리
        if(participationRepository.checkIfUserParticipated(uid, sid) == 1){
            throw new ParticipationException(ParticipationErrorCode.ALREADY_EXISTS);
        }

        // 해당 설문의 currentHeadCnt +1 증가
        surveyService.incrementHeadCnt(sid);

        // 중간테이블에 들어갈 새로운 ParticipatedSurvey 엔티티 만들어주기
        Participation newParticipation = Participation.builder()
                .user(userService.findById(uid))
                .survey(surveyService.getSurveyEntityById(sid))
                .build();

        participationRepository.save(newParticipation);

        // 참여시간 반환
        return newParticipation.getParticipatedAt().truncatedTo(ChronoUnit.MINUTES).toString();
    }

    @Transactional(readOnly = true)
    public List<SurveyResponse> getParticipatedSurveyByUser(int uid) {
        return participationRepository.getUserParticipationInfo(uid).stream()
                .map(info -> surveyService.getSurveyResponseById(info.sid())
                        .updateParticipatedAt(info.participated_at()))
                .toList();
    }
}
