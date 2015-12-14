package com.vini.vo;

import java.io.Serializable;

public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private Long registrationId;
    private Long surveyId;
    private String emailId;
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getRegistrationId() {
        return registrationId;
    }
    
    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }
    
    public Long getSurveyId() {
        return surveyId;
    }
    
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
    
    public String getEmailId() {
        return emailId;
    }
    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
}
