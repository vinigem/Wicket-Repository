package com.vini.service;

import java.io.Serializable;
import java.util.List;

import com.vini.vo.AnswerVO;
import com.vini.vo.SummaryVO;
import com.vini.vo.UserSurveyDetailsVO;

public interface IUserSurveyDetailsService extends Serializable{
    
    UserSurveyDetailsVO fetchOrCreateUserSurveyDetails(Long userId, Long surveyId);
    
    List<AnswerVO> fetchNextAnswers(Long questionSetId, int fromIndex, int max);
    
    void saveUserAnswers(UserSurveyDetailsVO userSurveyDetailsVO);
    
    List<SummaryVO> getAllUserSurveyDetails(Long surveyId);
    
}
