package notblank.boatvote.domain.participation.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.participation.dto.request.ParticipationRequest;
import notblank.boatvote.domain.participation.vo.ParticipationInfoVO;
import notblank.boatvote.domain.participation.entity.Participation;
import notblank.boatvote.domain.participation.repository.ParticipationRepository;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final SurveyService surveyService;
    private final UserService userService;

    private final ParticipationRepository participationRepository;

    @Transactional
    public LocalDateTime addNewParticipation(ParticipationRequest participationRequest) {

        // 해당 설문의 currentHeadCnt +1 증가
        surveyService.incrementHeadCnt(participationRequest.sid());

        // 중간테이블에 들어갈 새로운 ParticipatedSurvey 엔티티 만들어주기
        Participation newParticipation = Participation.builder()
                .user(userService.findById(participationRequest.uid()))
                .survey(surveyService.getSurveyEntityById(participationRequest.sid()))
                .build();

        participationRepository.save(newParticipation);

        // 참여시간 반환
        return newParticipation.getParticipatedAt();
    }

    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getParticipatedSurveyByUser(int uid) {

        List<ParticipationInfoVO> infoList = participationRepository.getUserParticipationInfo(uid);

        List<SurveyInfoResponse> surveyInfoResponseList = new ArrayList<>();

        for(ParticipationInfoVO info : infoList) {
            SurveyInfoResponse response = surveyService.getSurveyInfoResponseById(info.sid());
            surveyInfoResponseList.add(response.updateParticipatedAt(info.participated_at()));
        }

        return surveyInfoResponseList;
    }
}
