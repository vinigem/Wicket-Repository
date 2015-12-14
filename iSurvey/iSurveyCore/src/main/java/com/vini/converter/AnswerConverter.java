package com.vini.converter;

import java.util.ArrayList;
import java.util.List;

import com.vini.ec.Answer;
import com.vini.ec.Question;
import com.vini.vo.AnswerVO;

public class AnswerConverter implements ObjectConverter<Answer, AnswerVO>{
    
    @Override
    public AnswerVO convertSource(Answer answer) {
        if(answer == null){
            return null;
        }
        AnswerVO answerVO = new AnswerVO();
        answerVO.setAnswerId(answer.getAnswerId());
        answerVO.setQuestionId(answer.getQuestion().getQuestionId());
        answerVO.setQuestion(answer.getQuestion().getSurveyQuestion());
        answerVO.setAnswer(answer.getSurveyAnswer());
        answerVO.setOption(answer.getSelectedOption());
        
        return answerVO;
    }
    
    @Override
    public Answer convertTarget(AnswerVO answerVO) {
        Answer answer = new Answer();
        answer.setAnswerId(answerVO.getAnswerId());
        
        Question question = new Question();
        question.setQuestionId(answerVO.getQuestionId());
        answer.setQuestion(question);
        
        answer.setSurveyAnswer(answerVO.getAnswer());
        answer.setOption(answerVO.getOption());
        
        return answer;
    }
    
    @Override
    public List<AnswerVO> convertSources(List<Answer> answers) {
        List<AnswerVO> answerVOs = new ArrayList<AnswerVO>();
        for(Answer answer : answers){
            AnswerVO answerVO = convertSource(answer);
            answerVOs.add(answerVO);
        }
        return answerVOs;
    }
    
}
