package com.vini.ec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "UserSurveyDetails", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "surveyId" }) })
public class UserSurveyDetails extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userSurveyDetailsId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyId")
    private Survey survey;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userSurveyDetails", cascade= CascadeType.ALL)
    private List<Answer> answers;
    
    public Long getUserSurveyDetailsId() {
        return userSurveyDetailsId;
    }
    
    public void setUserSurveyAnswerId(Long userSurveyDetailsId) {
        this.userSurveyDetailsId = userSurveyDetailsId;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Survey getSurvey() {
        return survey;
    }
    
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
    
    public List<Answer> getAnswers() {
        return answers;
    }
    
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
    
    public void addAnswers(Answer answer){
        if(answers == null){
            answers = new ArrayList<Answer>();
        }
        answers.add(answer);
        answer.setUserSurveyDetails(this);
    }
    
    
}
