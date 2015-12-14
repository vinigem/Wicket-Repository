package com.vini.vo;

import java.io.Serializable;

public class AnswerVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long answerId;
    private Long questionId;
    private Integer option;
    private String answer;
    private String question;
    
    public Long getAnswerId() {
        return answerId;
    }
    
    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
    
    public Long getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    
    public Integer getOption() {
        return option;
    }
    
    public void setOption(Integer option) {
        this.option = option;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    
    
}
