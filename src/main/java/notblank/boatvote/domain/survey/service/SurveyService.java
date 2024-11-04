package notblank.boatvote.domain.survey.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.internal.FilterDTO;
import notblank.boatvote.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.boatvote.domain.survey.dto.request.CreateSurveyRequest;
import notblank.boatvote.domain.survey.dto.internal.OptionDTO;
import notblank.boatvote.domain.survey.dto.internal.QuestionDTO;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.utility.CodeConverter;
import notblank.boatvote.domain.utility.DTOConverter;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.service.UserService;
import notblank.boatvote.global.exception.errorcode.SurveyErrorCode;
import notblank.boatvote.global.exception.exception.SurveyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public SurveyResponse getSurveyResponseById(int id){
        Survey survey = getSurveyEntityById(id);
        return dtoConverter.toSurveyResponse(survey);
    }

    // 설문에 참여했을때 해당 설문의 currentHeadCnt를 1 증가시키는 함수
    @Transactional
    public void incrementHeadCnt(int sid){
        surveyRepository.incrementCurrentHeadCnt(sid);
    }

    @Transactional(readOnly = true)
    public List<SurveyResponse> getAllSurveys(){
        return surveyRepository.findAll()
                .stream().map(dtoConverter::toSurveyResponse)
                .toList();
    }

    // 특정 유저가 참여가능한 설문 조사
    @Transactional(readOnly = true)
    public List<SurveyResponse> getAvailableSurveys(int uid) {
        User participant = userService.findById(uid);

        int participantRegionCode = participant.getRegionCode();
        int participantJobCode = participant.getJobCode();
        int participantAgeCode = participant.getAgeCode();
        int participantGenderCode = participant.getGenderCode();

        return surveyRepository.getAvailableSurveyByParticipant(participantRegionCode, participantJobCode, participantAgeCode, participantGenderCode)
                .stream()
                .map(dtoConverter::toSurveyResponse)
                .toList();
    }

//    @Transactional(readOnly = true)
//    public List<SurveyResponse> getDeadLineSurveys(){
//
//    }

    @Transactional(readOnly = true)
    public List<SurveyResponse> getRequestedSurveys(int uid) {
        User owner = userService.findById(uid);

        return surveyRepository.getRequestedSurvey(uid)
                .stream()
                .map(dtoConverter::toSurveyResponse)
                .toList();
    }
}