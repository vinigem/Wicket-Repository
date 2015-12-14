package com.vini.converter;

import java.util.ArrayList;
import java.util.List;

import com.vini.ec.Answer;
import com.vini.ec.Survey;
import com.vini.ec.User;
import com.vini.ec.UserSurveyDetails;
import com.vini.vo.AnswerVO;
import com.vini.vo.UserSurveyDetailsVO;

public class UserSurveyDetailsConverter implements ObjectConverter<UserSurveyDetails, UserSurveyDetailsVO>{
    
    private AnswerConverter answerConverter = new AnswerConverter();
    
    @Override
    public UserSurveyDetailsVO convertSource(UserSurveyDetails userSurveyDetails) {
        if(userSurveyDetails == null){
            return null;
        }
        UserSurveyDetailsVO userSurveyDetailsVO = new UserSurveyDetailsVO();
        userSurveyDetailsVO.setUserSurveyDetailsId(userSurveyDetails.getUserSurveyDetailsId());
        userSurveyDetailsVO.setUserId(userSurveyDetails.getUser().getUserId());
        userSurveyDetailsVO.setSurveyId(userSurveyDetails.getSurvey().getSurveyId());
        userSurveyDetailsVO.setAnswers(answerConverter.convertSources(userSurveyDetails.getAnswers()));
        return userSurveyDetailsVO;
    }
    
    @Override
    public UserSurveyDetails convertTarget(UserSurveyDetailsVO userSurveyDetailsVO) {
        UserSurveyDetails userSurveyDetails = new UserSurveyDetails();
        userSurveyDetails.setUserSurveyAnswerId(userSurveyDetailsVO.getUserSurveyDetailsId());
        
        User user = new User();
        user.setUserId(userSurveyDetailsVO.getUserId());
        userSurveyDetails.setUser(user);
        
        Survey survey = new Survey();
        survey.setSurveyId(userSurveyDetailsVO.getSurveyId());
        userSurveyDetails.setSurvey(survey);
        
        for(AnswerVO answerVo : userSurveyDetailsVO.getAnswers()){
            Answer answer = answerConverter.convertTarget(answerVo);
            userSurveyDetails.addAnswers(answer);
        }
        
        return userSurveyDetails;
    }
    
    @Override
    public List<UserSurveyDetailsVO> convertSources(List<UserSurveyDetails> userSurveyDetails) {
        List<UserSurveyDetailsVO> serSurveyDetailsVOs = new ArrayList<UserSurveyDetailsVO>();
        for(UserSurveyDetails userSurveyDetail : userSurveyDetails){
            UserSurveyDetailsVO userSurveyDetailVO = convertSource(userSurveyDetail);
            serSurveyDetailsVOs.add(userSurveyDetailVO);
        }
        return serSurveyDetailsVOs;
    }
    
}
