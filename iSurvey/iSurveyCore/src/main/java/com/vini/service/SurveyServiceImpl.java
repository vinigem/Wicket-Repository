package com.vini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vini.converter.SurveyConverter;
import com.vini.dao.IQuestionSetDao;
import com.vini.dao.ISurveyDao;
import com.vini.ec.QuestionSet;
import com.vini.ec.Survey;
import com.vini.vo.SurveyVO;

@Service
public class SurveyServiceImpl implements ISurveyService {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ISurveyDao surveyDao;
    
    @Autowired 
    private IQuestionSetDao questionSetDao;
    
    private transient SurveyConverter converter = new SurveyConverter();
    
    @Transactional
    @Override
    public SurveyVO createSurvey(SurveyVO surveyVO) {
        return converter.convertSource(surveyDao.create(converter.convertTarget(surveyVO)));
        
    }
    
    @Transactional
    @Override
    public SurveyVO updateSurvey(SurveyVO surveyVO) {
        return converter.convertSource(surveyDao.update(converter.convertTarget(surveyVO)));
    }
    
    @Transactional
    @Override
    public List<String> getSurveyNames(String input) {
        return surveyDao.getSurveyNames(input);
    }
    
    @Transactional
    @Override
    public SurveyVO getSurveyByName(String surveyName) {
        return converter.convertSource(surveyDao.getSurveyByName(surveyName));
    }
    
    @Transactional
    @Override
    public List<SurveyVO> getAllSurveys() {
        return converter.convertSources(surveyDao.getAllSurveys());
    }
    
    @Transactional
    @Override
    public SurveyVO getSurveyById(Long surveyId) {
        return converter.convertSource(surveyDao.findById(surveyId));
    }
    
    @Transactional
    @Override
    public void linkSurveyQuestionSet(SurveyVO selectedSurvey) {
        surveyDao.update(converter.convertTarget(selectedSurvey));
    }
    
    @Transactional
    @Override
    public void linkSurveyQuestionSet(Long questionSetId, Long surveyId) {
        QuestionSet questionSet = questionSetDao.findById(questionSetId);
        
        Survey survey = surveyDao.findById(surveyId);
        questionSet.addSurvey(survey);
        
        questionSetDao.update(questionSet);
    }
    
}
