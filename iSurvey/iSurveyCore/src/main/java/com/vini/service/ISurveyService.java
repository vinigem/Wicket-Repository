package com.vini.service;

import java.io.Serializable;
import java.util.List;

import com.vini.vo.SurveyVO;

public interface ISurveyService extends Serializable{
    
    SurveyVO createSurvey(SurveyVO surveyVO);
    
    SurveyVO updateSurvey(SurveyVO modelObject);
    
    List<String> getSurveyNames(String input);
    
    SurveyVO getSurveyByName(String surveyName);
    
    SurveyVO getSurveyById(Long surveyId);
    
    List<SurveyVO> getAllSurveys();
    
    void linkSurveyQuestionSet(SurveyVO selectedSurvey);
    
    void linkSurveyQuestionSet(Long questionSetId, Long surveyId);
    
    
}
