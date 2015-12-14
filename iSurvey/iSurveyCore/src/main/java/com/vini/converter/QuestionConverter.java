package com.vini.converter;

import java.util.ArrayList;
import java.util.List;

import com.vini.ec.Question;
import com.vini.vo.QuestionVO;

public class QuestionConverter implements ObjectConverter<Question, QuestionVO>{
    
    @Override
    public QuestionVO convertSource(Question question) {
        if(question == null){
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        questionVO.setQuestionId(question.getQuestionId());
        questionVO.setQuestion(question.getSurveyQuestion());
        
        return questionVO;
    }
    
    @Override
    public Question convertTarget(QuestionVO questionVO) {
        Question question = new Question();
        question.setQuestionId(questionVO.getQuestionId());
        question.setSurveyQuestion(questionVO.getQuestion());
        
        return question;
    }
    
    @Override
    public List<QuestionVO> convertSources(List<Question> questions) {
        List<QuestionVO> questionVOs = new ArrayList<QuestionVO>();
        for(Question question : questions){
            QuestionVO questionVO = convertSource(question);
            questionVOs.add(questionVO);
        }
        return questionVOs;
    }
    
}
