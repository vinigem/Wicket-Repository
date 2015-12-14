package com.vini.converter;

import java.util.ArrayList;
import java.util.List;

import com.vini.ec.QuestionSet;
import com.vini.ec.Survey;
import com.vini.vo.SurveyVO;

public class SurveyConverter implements ObjectConverter<Survey, SurveyVO> {
    
    @Override
    public SurveyVO convertSource(Survey survey) {
        if(survey == null){
            return null;
        }
        SurveyVO surveyVO = new SurveyVO();
        surveyVO.setSurveyId(survey.getSurveyId());
        surveyVO.setSurveyName(survey.getSurveyName());
        surveyVO.setStartDate(survey.getStartDate());
        surveyVO.setEndDate(survey.getEndDate());
        surveyVO.setDescription(survey.getDescription());
        
        if(survey.getQuestionSet() != null){
            surveyVO.setQuestionSetId(survey.getQuestionSet().getQuestionSetId());
        }
        
        return surveyVO;
    }
    
    @Override
    public Survey convertTarget(SurveyVO surveyVO) {
        if(surveyVO == null){
            return null;
        }
        Survey survey = new Survey();
        survey.setSurveyId(surveyVO.getSurveyId());
        survey.setSurveyName(surveyVO.getSurveyName());
        survey.setStartDate(surveyVO.getStartDate());
        survey.setEndDate(surveyVO.getEndDate());
        survey.setDescription(surveyVO.getDescription());
        
        if(surveyVO.getQuestionSetId() != null){
            QuestionSet questionSet = new QuestionSet();
            questionSet.setQuestionSetId(surveyVO.getQuestionSetId());
            survey.setQuestionSet(questionSet);
        }
        
        return survey;
    }
    
    @Override
    public List<SurveyVO> convertSources(List<Survey> surveys) {
        List<SurveyVO> surveyVOs = new ArrayList<SurveyVO>();
        for(Survey survey : surveys){
            SurveyVO surveyVO = convertSource(survey);
            surveyVOs.add(surveyVO);
        }
        return surveyVOs;
    }
    
}
