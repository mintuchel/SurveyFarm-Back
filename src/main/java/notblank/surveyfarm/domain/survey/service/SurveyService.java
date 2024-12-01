package notblank.surveyfarm.domain.survey.service;

import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.survey.dto.request.CreateSurveyRequest;
import notblank.surveyfarm.domain.survey.dto.response.SurveyFilterResponse;
import notblank.surveyfarm.domain.survey.dto.response.SurveyInfoResponse;
import notblank.surveyfarm.domain.survey.dto.response.SurveyQuestionListResponse;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.survey.repository.SurveyRepository;
import notblank.surveyfarm.domain.utility.DTOConverter;
import notblank.surveyfarm.domain.user.entity.User;
import notblank.surveyfarm.domain.user.service.UserService;
import notblank.surveyfarm.global.exception.errorcode.SurveyErrorCode;
import notblank.surveyfarm.global.exception.exception.SurveyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final UserService userService;

    private final SurveyRepository surveyRepository;

    private final DTOConverter dtoConverter;

    // 설문 조회
    @Transactional(readOnly = true)
    public Survey getSurveyEntityById(int id){
        return surveyRepository.findById(id)
                .orElseThrow(()-> new SurveyException(SurveyErrorCode.SURVEY_NOT_FOUND));
    }

    // 새로운 설문 추가
    @Transactional
    public int addNewSurvey(CreateSurveyRequest createSurveyRequest){
        User owner = userService.findById(createSurveyRequest.surveyInfo().uid());
        Survey survey = dtoConverter.toSurveyEntity(createSurveyRequest, owner);

        surveyRepository.save(survey);

        // 연관관계 편의메서드
        survey.getOwner().addRequestedSurvey(survey);

        return survey.getId();
    }

    // 설문 엔티티를 SurveyInfoResponse 로 반환
    @Transactional(readOnly = true)
    public SurveyInfoResponse getSurveyInfoById(int sid){
        Survey survey = getSurveyEntityById(sid);
        return dtoConverter.toSurveyInfoResponse(survey);
    }

    @Transactional(readOnly = true)
    public SurveyFilterResponse getSurveyFilterById(int sid){
        Survey survey = getSurveyEntityById(sid);
        return dtoConverter.toSurveyFilterResponse(survey);
    }

    @Transactional(readOnly = true)
    public SurveyQuestionListResponse getSurveyQuestionListById(int sid){
        Survey survey = getSurveyEntityById(sid);
        return dtoConverter.toSurveyQuestionListResponse(survey);
    }

    // 설문에 참여했을때 해당 설문의 currentHeadCnt를 1 증가시키는 함수
    @Transactional
    public void incrementHeadCnt(int sid){
        surveyRepository.incrementCurrentHeadCnt(sid);
    }

    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getAllSurveys(){
        return surveyRepository.findAll()
                .stream().map(dtoConverter::toSurveyInfoResponse)
                .toList();
    }

    // 특정 유저가 참여가능한 설문 조사
    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getAvailableSurveys(int uid, int page) {
        User participant = userService.findById(uid);

        int participantRegionCode = participant.getRegionCode();
        int participantJobCode = participant.getJobCode();
        int participantAgeCode = participant.getAgeCode();
        int participantGenderCode = participant.getGenderCode();

        int offset = (page - 1) * 30;

        return surveyRepository.getAvailableSurveyByParticipant(participantRegionCode, participantJobCode, participantAgeCode, participantGenderCode, offset)
                .stream()
                .map(dtoConverter::toSurveyInfoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getDeadLineSurveys(){
        return surveyRepository.getDeadlineUpcomingSurveys()
                .stream()
                .map(dtoConverter::toSurveyInfoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getTrendingSurveys(){
        return surveyRepository.getTrendingSurveys()
                .stream()
                .map(dtoConverter::toSurveyInfoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getRequestedSurveys(int uid) {
        User owner = userService.findById(uid);

        return surveyRepository.getRequestedSurvey(uid)
                .stream()
                .map(dtoConverter::toSurveyInfoResponse)
                .toList();
    }
}