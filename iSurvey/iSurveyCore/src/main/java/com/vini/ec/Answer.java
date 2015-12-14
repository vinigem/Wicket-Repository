package com.vini.ec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Answer")
public class Answer extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "answerId")
    private Long answerId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;
    
    @Column(name = "selectedOption")
    private Integer selectedOption;
    
    @Column(name = "answer")
    private String surveyAnswer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSurveyDetailsId")
    private UserSurveyDetails userSurveyDetails;
    
    public Long getAnswerId() {
        return answerId;
    }
    
    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
    
    public Question getQuestion() {
        return question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
    
    public Integer getSelectedOption() {
        return selectedOption;
    }
    
    public void setOption(Integer selectedOption) {
        this.selectedOption = selectedOption;
    }
    
    public String getSurveyAnswer() {
        return surveyAnswer;
    }
    
    public void setSurveyAnswer(String surveyAnswer) {
        this.surveyAnswer = surveyAnswer;
    }
    
    public UserSurveyDetails getUserSurveyDetails() {
        return userSurveyDetails;
    }
    
    public void setUserSurveyDetails(UserSurveyDetails userSurveyDetails) {
        this.userSurveyDetails = userSurveyDetails;
    }
    
    
    
}
