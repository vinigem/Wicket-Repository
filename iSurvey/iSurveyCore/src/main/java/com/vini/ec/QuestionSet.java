package com.vini.ec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Question_Set")
public class QuestionSet extends BaseEntity {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "questionSetId")
    private Long questionSetId;
    
    @Column(name = "questionSetName", unique = true, nullable = false)
    private String questionSetName;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionSet", cascade= CascadeType.ALL)
    private List<Question >questions;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionSet", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Survey> surveys;
    
    public Long getQuestionSetId() {
        return questionSetId;
    }
    
    public void setQuestionSetId(Long questionSetId) {
        this.questionSetId = questionSetId;
    }
    
    public String getQuestionSetName() {
        return questionSetName;
    }
    
    public void setQuestionSetName(String questionSetName) {
        this.questionSetName = questionSetName;
    }
    
    public List<Question> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    public List<Survey> getSurveys() {
        return surveys;
    }
    
    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }
    
    public void addQuestion(Question question){
        if(questions == null){
            questions = new ArrayList<Question>();
        }
        questions.add(question);
        question.setQuestionSet(this);
    }
    
    public void addSurvey(Survey survey){
        if(surveys == null){
            surveys = new ArrayList<Survey>();
        }
        surveys.add(survey);
        survey.setQuestionSet(this);
    }
    
    
    
}
