package com.vini.ec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Question", uniqueConstraints = { @UniqueConstraint(columnNames = { "question", "questionSetId" }) })
public class Question extends BaseEntity {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "questionId")
    private Long questionId;
    
    @Column(name = "question", nullable = false)
    private String surveyQuestion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionSetId")
    private QuestionSet questionSet;
    
    public Long getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    
    public String getSurveyQuestion() {
        return surveyQuestion;
    }
    
    public void setSurveyQuestion(String question) {
        this.surveyQuestion = question;
    }
    
    public QuestionSet getQuestionSet() {
        return questionSet;
    }
    
    public void setQuestionSet(QuestionSet questionSet) {
        this.questionSet = questionSet;
    }
    
    
    
}
