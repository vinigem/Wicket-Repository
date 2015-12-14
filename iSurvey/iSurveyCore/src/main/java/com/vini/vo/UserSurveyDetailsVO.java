package com.vini.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserSurveyDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long userSurveyDetailsId;
    private Long userId;
    private Long surveyId;
    private List<AnswerVO> answers;
    
    public Long getUserSurveyDetailsId() {
        return userSurveyDetailsId;
    }
    
    public void setUserSurveyDetailsId(Long userSurveyDetailsId) {
        this.userSurveyDetailsId = userSurveyDetailsId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getSurveyId() {
        return surveyId;
    }
    
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
    
    public List<AnswerVO> getAnswers() {
        return answers;
    }
    
    public void setAnswers(List<AnswerVO> answers) {
        this.answers = answers;
    }
    
    public void addAnswers(List<AnswerVO> answers) {
        if(this.answers == null){
            this.answers = new ArrayList<AnswerVO>();
        }
        for(AnswerVO answerVO : answers){
            if(!this.answers.contains(answerVO)){
                this.answers.add(answerVO);
            }
        }
    }
    
    
    
}
