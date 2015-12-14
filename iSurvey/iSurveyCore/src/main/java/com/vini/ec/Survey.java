package com.vini.ec;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Survey")
public class Survey extends BaseEntity{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "surveyId")
    private Long surveyId;
    
    @Column(name = "surveyName", nullable = false, unique = true)
    private String surveyName;
    
    @Column(name = "startDate", columnDefinition = "DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "endDate", columnDefinition = "DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionSetId")
    private QuestionSet questionSet;
    
    
    public Long getSurveyId() {
        return surveyId;
    }
    
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
    
    public String getSurveyName() {
        return surveyName;
    }
    
    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public QuestionSet getQuestionSet() {
        return questionSet;
    }
    
    public void setQuestionSet(QuestionSet questionSet) {
        this.questionSet = questionSet;
    }
    
    
}
