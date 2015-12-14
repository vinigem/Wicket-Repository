package com.vini.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class SurveyVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long surveyId;
    private String surveyName;
    private Date startDate;
    private Date endDate;
    private String description;
    private Long questionSetId;
    
    public Long getSurveyId() {
        return this.surveyId;
    }
    
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
    
    public String getSurveyName() {
        return this.surveyName;
    }
    
    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }
    
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        if(this.surveyId == null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DATE, 1);
            cal.add(Calendar.SECOND, -1);
            this.endDate = cal.getTime();
        }else{
            this.endDate = endDate;
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Long getQuestionSetId() {
        return this.questionSetId;
    }
    
    public void setQuestionSetId(Long questionSetId) {
        this.questionSetId = questionSetId;
    }
    
    
}
