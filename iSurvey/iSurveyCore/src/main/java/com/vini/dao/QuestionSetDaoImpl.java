package com.vini.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.vini.constants.SurveyConstant;
import com.vini.ec.Question;
import com.vini.ec.QuestionSet;

@Repository
public class QuestionSetDaoImpl extends GenericDaoImpl<QuestionSet> implements IQuestionSetDao{
    private static final long serialVersionUID = 1L;
    
    @Override
    public List<String> getQuestionSetNames(String input) {
        TypedQuery<String> query = entityManager.createQuery("SELECT questionSetName FROM QuestionSet WHERE LOWER(questionSetName) like :questionSetName ORDER BY questionSetName", String.class);
        query.setParameter(SurveyConstant.QUESTION_SET_NAME, "%" +input.toLowerCase()+ "%");
        return query.getResultList();
    }
    
    @Override
    public List<String> getQuestionsBySetName(String quesSetName) {
        TypedQuery<String> query = entityManager.createQuery("SELECT q.surveyQuestion FROM QuestionSet qs JOIN qs.questions q WHERE qs.questionSetName = :questionSetName ORDER BY q.questionId", String.class);
        query.setParameter(SurveyConstant.QUESTION_SET_NAME, quesSetName);
        return query.getResultList();
    }
    
    @Override
    public QuestionSet getQuestionsSetByName(String quesSetName) {
        TypedQuery<QuestionSet> query = entityManager.createQuery("FROM QuestionSet WHERE questionSetName = :questionSetName", QuestionSet.class);
        query.setParameter(SurveyConstant.QUESTION_SET_NAME, quesSetName);
        List<QuestionSet> questionSets = query.getResultList();
        return questionSets.isEmpty() ? null : questionSets.get(0);
    }
    
    @Override
    public void deleteQuestionsSetByName(String questionSetName) {
        TypedQuery<QuestionSet> query = entityManager.createQuery("FROM QuestionSet WHERE questionSetName = :questionSetName", QuestionSet.class);
        query.setParameter(SurveyConstant.QUESTION_SET_NAME, questionSetName);
        List<QuestionSet> questionSets = query.getResultList();
        
        if(!questionSets.isEmpty()){
            entityManager.remove(questionSets.get(0));
            entityManager.flush();
        }
    }
    
    @Override
    public List<Question> getQuestionsBySetId(Long questionSetId) {
        TypedQuery<Question> query = entityManager.createQuery("FROM Question WHERE questionSetId = :questionSetId ORDER BY questionId", Question.class);
        query.setParameter("questionSetId", questionSetId);
        return query.getResultList();
    }
    
    @Override
    public List<QuestionSet> getAllQuestionsSets() {
        TypedQuery<QuestionSet> query = entityManager.createQuery("FROM QuestionSet ORDER BY questionSetId", QuestionSet.class);
        return query.getResultList();
    }
    
    
    
}
