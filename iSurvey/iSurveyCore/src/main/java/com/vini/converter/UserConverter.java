package com.vini.converter;

import java.util.Collections;
import java.util.List;

import com.vini.ec.Survey;
import com.vini.ec.User;
import com.vini.vo.UserVO;

public class UserConverter implements ObjectConverter<User, UserVO>{
    
    @Override
    public UserVO convertSource(User user) {
        if(user == null){
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setEmailId(user.getEmailId());
        userVO.setRegistrationId(user.getRegistrationId());
        userVO.setSurveyId(user.getSurvey().getSurveyId());
        userVO.setUserId(user.getUserId());
        
        return userVO;
    }
    
    @Override
    public User convertTarget(UserVO userVO) {
        User user = new User();
        user.setEmailId(userVO.getEmailId());
        user.setRegistrationId(userVO.getRegistrationId());
        
        Survey survey = new Survey();
        survey.setSurveyId(userVO.getSurveyId());
        user.setSurvey(survey);
        
        user.setUserId(userVO.getUserId());
        
        return user;
    }
    
    @Override
    public List<UserVO> convertSources(List<User> object) {
        return Collections.emptyList();
    }
    
}
