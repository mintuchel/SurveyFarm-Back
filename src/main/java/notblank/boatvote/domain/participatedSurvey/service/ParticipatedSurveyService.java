package notblank.boatvote.domain.participatedSurvey.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.participatedSurvey.dto.request.ParticipateRequest;
import notblank.boatvote.domain.participatedSurvey.entity.ParticipatedSurvey;
import notblank.boatvote.domain.participatedSurvey.repository.ParticipatedSurveyRepository;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipatedSurveyService {

    private final ParticipatedSurveyRepository participatedSurveyRepository;
    private final SurveyService surveyService;
    private final UserService userService;

    @Transactional
    public LocalDateTime participateSurvey(ParticipateRequest participateRequest) {
        ParticipatedSurvey newParticipatedSurvey = ParticipatedSurvey.builder()
                .user(userService.findUserById(participateRequest.uid()))
                .survey(surveyService.findSurveyByUID(participateRequest.sid()))
                .build();

        participatedSurveyRepository.save(newParticipatedSurvey);

        // 참여시간 반환
        return newParticipatedSurvey.getParticipatedAt();
    }

    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getParticipatedSurveys(int uid) {
        return participatedSurveyRepository.findByUid(uid).stream()
                .map(dto -> {
                    SurveyInfoResponse response = surveyService.getSurveyById(dto.sid());
                    return response.updateParticipatedAt(dto.participated_at());
                })
                .collect(Collectors.toList());
    }
}
