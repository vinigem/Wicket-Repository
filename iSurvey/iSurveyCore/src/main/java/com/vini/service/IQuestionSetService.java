package com.vini.service;

import java.io.Serializable;
import java.util.List;

import com.vini.ec.QuestionSet;
import com.vini.vo.QuestionSetVO;
import com.vini.vo.QuestionVO;

public interface IQuestionSetService extends Serializable{
    
    List<String> getQuestionSetNames(String input);
    
    List<String> getQuestionsBySetName(String quesSetName);
    
    boolean createQuestionSet(String questionSetName, List<String> object);
    
    void deleteQuestionsSetByName(String quesSetName);
    
    QuestionSet createQuestionSet(QuestionSet questionSet);
    
    List<QuestionVO> getQuestionsBySetId(Long questionSetId);
    
    List<QuestionSetVO> getAllQuestionsSets();
    
}
