package com.vini.dao;

import java.util.List;

import com.vini.ec.Question;
import com.vini.ec.UserSurveyDetails;

public interface IUserSurveyDetailsDao extends GenericDao<UserSurveyDetails>{
    
    UserSurveyDetails fetchUserSurveyDetails(Long userId, Long surveyId);
    
    List<Question> fetchNextQuestions(Long questionSetId, int fromIndex, int max);
    
    List<UserSurveyDetails> getAllUserSurveyDetails(Long surveyId);
    
    
}
