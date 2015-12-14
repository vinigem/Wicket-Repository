package com.vini.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.vini.constants.SurveyConstant;
import com.vini.ec.Survey;

@Repository
public class SurveyDaoImpl extends GenericDaoImpl<Survey> implements ISurveyDao {
    private static final long serialVersionUID = 1L;
    
    @Override
    public List<String> getSurveyNames(String input) {
        TypedQuery<String> query = entityManager.createQuery("SELECT surveyName FROM Survey WHERE LOWER(surveyName) like :surveyName ORDER BY surveyName", String.class);
        query.setParameter(SurveyConstant.SURVEY_NAME, "%" +input.toLowerCase()+ "%");
        return query.getResultList();
    }
    
    @Override
    public Survey getSurveyByName(String surveyName) {
        TypedQuery<Survey> query = entityManager.createQuery("FROM Survey WHERE surveyName = :surveyName", Survey.class);
        query.setParameter(SurveyConstant.SURVEY_NAME, surveyName);
        List<Survey> surveys = query.getResultList();
        return  surveys.isEmpty() ? null : surveys.get(0);
    }
    
    @Override
    public List<Survey> getAllSurveys() {
        TypedQuery<Survey> query = entityManager.createQuery("FROM Survey", Survey.class);
        return query.getResultList();
    }
    
    
}
