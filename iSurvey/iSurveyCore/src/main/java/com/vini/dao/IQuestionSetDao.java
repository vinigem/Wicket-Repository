package com.vini.dao;

import java.util.List;

import com.vini.ec.Question;
import com.vini.ec.QuestionSet;

public interface IQuestionSetDao extends GenericDao<QuestionSet>{
    
    List<String> getQuestionSetNames(String input);
    
    List<String> getQuestionsBySetName(String quesSetName);
    
    QuestionSet getQuestionsSetByName(String questionSetName);
    
    void deleteQuestionsSetByName(String questionSetName);
    
    List<Question> getQuestionsBySetId(Long questionSetId);
    
    List<QuestionSet> getAllQuestionsSets();
    
}
