package com.vini.dao;

import java.util.List;

import com.vini.ec.Survey;

public interface ISurveyDao extends GenericDao<Survey>{
    
    List<String> getSurveyNames(String input);
    
    Survey getSurveyByName(String surveyName);
    
    List<Survey> getAllSurveys();
    
    
}
