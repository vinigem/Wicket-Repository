package com.vini.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vini.converter.UserSurveyDetailsConverter;
import com.vini.dao.IUserSurveyDetailsDao;
import com.vini.ec.Answer;
import com.vini.ec.Question;
import com.vini.ec.Survey;
import com.vini.ec.User;
import com.vini.ec.UserSurveyDetails;
import com.vini.vo.AnswerVO;
import com.vini.vo.SummaryVO;
import com.vini.vo.UserSurveyDetailsVO;

@Service
public class UserSurveyDetailsServiceImpl implements IUserSurveyDetailsService {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IUserSurveyDetailsDao userSurveyDetailsDao;
    private transient UserSurveyDetailsConverter converter = new UserSurveyDetailsConverter();
    
    @Transactional
    @Override
    public UserSurveyDetailsVO fetchOrCreateUserSurveyDetails(Long userId, Long surveyId) {
        
        UserSurveyDetails userSurveyDetails = userSurveyDetailsDao.fetchUserSurveyDetails(userId, surveyId);
        if(userSurveyDetails == null){
            userSurveyDetails = new UserSurveyDetails();
            User user = new User();
            user.setUserId(userId);
            userSurveyDetails.setUser(user);
            
            Survey survey = new Survey();
            survey.setSurveyId(surveyId);
            userSurveyDetails.setSurvey(survey);
            
            userSurveyDetails.setAnswers(new ArrayList<Answer>());
        }
        
        return converter.convertSource(userSurveyDetailsDao.create(userSurveyDetails));
    }
    
    @Override
    public List<AnswerVO> fetchNextAnswers(Long questionSetId, int fromIndex, int max) {
        List<Question> questions = userSurveyDetailsDao.fetchNextQuestions(questionSetId, fromIndex, max);
        List<AnswerVO> answerVOs = new ArrayList<AnswerVO>();
        
        for(Question question : questions){
            AnswerVO answerVO = new AnswerVO();
            answerVO.setQuestionId(question.getQuestionId());
            answerVO.setQuestion(question.getSurveyQuestion());
            answerVOs.add(answerVO);
        }
        return answerVOs;
    }
    
    @Transactional
    @Override
    public void saveUserAnswers(UserSurveyDetailsVO userSurveyDetailsVO) {
        userSurveyDetailsDao.update(converter.convertTarget(userSurveyDetailsVO));		
    }
    
    @Transactional
    @Override
    public List<SummaryVO> getAllUserSurveyDetails(Long surveyId) {
        
        List<UserSurveyDetailsVO> userSurveyDetailsVOs =  converter.convertSources(userSurveyDetailsDao.getAllUserSurveyDetails(surveyId));
        Map<Long, List<Integer>> answersMap = new HashMap<Long, List<Integer>>();
        Map<Long, Set<String>> questionsMap = new HashMap<Long, Set<String>>();
        
        for(UserSurveyDetailsVO surveyDetailsVO : userSurveyDetailsVOs){
            
            for(AnswerVO answerVO : surveyDetailsVO.getAnswers()){
                // Add the questions
                if(!questionsMap.containsKey(answerVO.getQuestionId())){
                    questionsMap.put(answerVO.getQuestionId(), new HashSet<String>());
                }
                questionsMap.get(answerVO.getQuestionId()).add(answerVO.getQuestion());
                
                // Add the answers
                if(!answersMap.containsKey(answerVO.getQuestionId())){
                    answersMap.put(answerVO.getQuestionId(), new ArrayList<Integer>());
                }
                answersMap.get(answerVO.getQuestionId()).add(answerVO.getOption());
            }
        }
        
        List<SummaryVO> summaryVOs = new ArrayList<SummaryVO>();
        
        for (Entry<Long, Set<String>> entry : questionsMap.entrySet()){
            Set<String> questions = entry.getValue();
            
            for(String question : questions){
                SummaryVO summaryVO = new SummaryVO();
                summaryVO.setQuestion(question);
                summaryVO.setOptions(answersMap.get(entry.getKey()));
                summaryVOs.add(summaryVO);
            }
        }	
        return summaryVOs;
    }
    
}
