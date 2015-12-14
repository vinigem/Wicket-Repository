package com.vini.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SurveySummaryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Map<Long, List<Integer>> answersMap;
    private Map<Long, Set<String>> questionsMap;
    
    
    public Map<Long, List<Integer>> getAnswersMap() {
        return answersMap;
    }
    
    public void setAnswersMap(Map<Long, List<Integer>> answersMap) {
        this.answersMap = answersMap;
    }
    
    public Map<Long, Set<String>> getQuestionsMap() {
        return questionsMap;
    }
    
    public void setQuestionsMap(Map<Long, Set<String>> questionsMap) {
        this.questionsMap = questionsMap;
    }
    
    
    
}
