package com.vini.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionSetVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long questionSetId;
    private String questionSetName;
    private List<QuestionVO>questions;
    
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
    
    public List<QuestionVO> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<QuestionVO> questions) {
        this.questions = questions;
    }
    
    public void addQuestion(QuestionVO question) {
        if(questions == null){
            questions = new ArrayList<QuestionVO>();
        }
        questions.add(question);
    }
    
}
