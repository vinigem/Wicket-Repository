package com.vini.converter;

import java.util.ArrayList;
import java.util.List;

import com.vini.ec.Question;
import com.vini.ec.QuestionSet;
import com.vini.vo.QuestionSetVO;
import com.vini.vo.QuestionVO;

public class QuestionSetConverter implements ObjectConverter<QuestionSet, QuestionSetVO>{
    
    QuestionConverter converter = new QuestionConverter();
    
    @Override
    public QuestionSetVO convertSource(QuestionSet questionSet) {
        if(questionSet == null){
            return null;
        }
        
        QuestionSetVO questionSetVO = new QuestionSetVO();
        questionSetVO.setQuestionSetId(questionSet.getQuestionSetId());
        questionSetVO.setQuestionSetName(questionSet.getQuestionSetName());
        questionSetVO.setQuestions(converter.convertSources(questionSet.getQuestions()));
        
        return questionSetVO;
    }
    
    @Override
    public QuestionSet convertTarget(QuestionSetVO questionSetVO) {
        QuestionSet questionSet = new QuestionSet();
        questionSet.setQuestionSetId(questionSetVO.getQuestionSetId());
        questionSet.setQuestionSetName(questionSetVO.getQuestionSetName());
        
        for(QuestionVO questionVO : questionSetVO.getQuestions()){
            Question question = converter.convertTarget(questionVO);
            questionSet.addQuestion(question);
        }
        
        return questionSet;
    }
    
    @Override
    public List<QuestionSetVO> convertSources(List<QuestionSet> questionSets) {
        List<QuestionSetVO> questionSetVOs = new ArrayList<QuestionSetVO>();
        
        for(QuestionSet questionSet : questionSets){
            QuestionSetVO questionSetVO = convertSource(questionSet);
            questionSetVOs.add(questionSetVO);
        }
        return questionSetVOs;
    }
    
    
}
