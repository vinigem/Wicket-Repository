package com.vini.vo;

import java.io.Serializable;
import java.util.List;

public class SummaryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String question;
    private List<Integer> options;
    
    public SummaryVO() {
        
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public List<Integer> getOptions() {
        return options;
    }
    
    public void setOptions(List<Integer> options) {
        this.options = options;
    }
    
    
    
}
