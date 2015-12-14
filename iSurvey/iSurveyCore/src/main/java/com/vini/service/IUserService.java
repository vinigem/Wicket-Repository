package com.vini.service;

import java.io.Serializable;

import com.vini.vo.UserVO;

public interface IUserService extends Serializable{
    
    UserVO getUserByEmailId(String emailId);
    
    UserVO getUserByRegistrationId(long registrationId);
    
    UserVO registerUserForSurvey(UserVO userVO);
    
}
