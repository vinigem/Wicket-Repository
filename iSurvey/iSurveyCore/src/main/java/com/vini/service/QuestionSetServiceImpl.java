package com.vini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vini.converter.QuestionConverter;
import com.vini.converter.QuestionSetConverter;
import com.vini.dao.IQuestionSetDao;
import com.vini.ec.Question;
import com.vini.ec.QuestionSet;
import com.vini.vo.QuestionSetVO;
import com.vini.vo.QuestionVO;

@Service
public class QuestionSetServiceImpl implements IQuestionSetService {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IQuestionSetDao questionSetDao;
    
    private transient QuestionConverter converter = new QuestionConverter();
    private transient QuestionSetConverter questionSetConverter = new QuestionSetConverter();
    
    @Transactional
    @Override
    public QuestionSet createQuestionSet(QuestionSet questionSet){
        return questionSetDao.create(questionSet);
    }
    
    @Transactional
    @Override
    public List<String> getQuestionSetNames(String input) {
        return questionSetDao.getQuestionSetNames(input);
    }
    
    @Transactional
    @Override
    public List<String> getQuestionsBySetName(String quesSetName) {
        return questionSetDao.getQuestionsBySetName(quesSetName);
    }
    
    @Transactional
    @Override
    public void deleteQuestionsSetByName(String quesSetName) {
        questionSetDao.deleteQuestionsSetByName(quesSetName);
    }
    
    @Transactional
    @Override
    public boolean createQuestionSet(String questionSetName, List<String> questions) {
        deleteQuestionsSetByName(questionSetName);
        
        QuestionSet questionSet = new QuestionSet();
        questionSet.setQuestionSetName(questionSetName);
        for(String ques : questions){
            Question question = new Question();
            question.setSurveyQuestion(ques);
            questionSet.addQuestion(question);
        }
        createQuestionSet(questionSet);
        return true;
    }
    
    @Transactional
    @Override
    public List<QuestionVO> getQuestionsBySetId(Long questionSetId) {
        return converter.convertSources(questionSetDao.getQuestionsBySetId(questionSetId));
    }
    
    @Transactional
    @Override
    public List<QuestionSetVO> getAllQuestionsSets() {
        return questionSetConverter.convertSources(questionSetDao.getAllQuestionsSets());
    }
    
}
