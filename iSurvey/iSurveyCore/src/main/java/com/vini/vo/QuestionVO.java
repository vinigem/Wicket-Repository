package com.vini.vo;

import java.io.Serializable;

public class QuestionVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long questionId;
    private String question;
    
    public Long getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
}
