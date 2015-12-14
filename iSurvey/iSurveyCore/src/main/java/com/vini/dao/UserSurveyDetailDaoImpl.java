package com.vini.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.vini.ec.Question;
import com.vini.ec.UserSurveyDetails;

@Repository
public class UserSurveyDetailDaoImpl extends GenericDaoImpl<UserSurveyDetails> implements IUserSurveyDetailsDao{
    private static final long serialVersionUID = 1L;
    
    @Override
    public UserSurveyDetails fetchUserSurveyDetails(Long userId, Long surveyId) {
        TypedQuery<UserSurveyDetails> query = entityManager.createQuery("FROM UserSurveyDetails WHERE userId = :userId AND surveyId = :surveyId", UserSurveyDetails.class);
        query.setParameter("userId", userId);
        query.setParameter("surveyId", surveyId);
        List<UserSurveyDetails> userSurveyDetails = query.getResultList();
        return userSurveyDetails.isEmpty() ? null : userSurveyDetails.get(0);
    }
    
    @Override
    public List<Question> fetchNextQuestions(Long questionSetId, int fromIndex, int max) {
        TypedQuery<Question> query = entityManager.createQuery("FROM Question WHERE questionSetId = :questionSetId order by questionId", Question.class);
        query.setParameter("questionSetId", questionSetId);
        return query.setFirstResult(fromIndex).setMaxResults(max).getResultList();
    }
    
    @Override
    public List<UserSurveyDetails> getAllUserSurveyDetails(Long surveyId) {
        TypedQuery<UserSurveyDetails> query = entityManager.createQuery("FROM UserSurveyDetails WHERE surveyId = :surveyId", UserSurveyDetails.class);
        query.setParameter("surveyId", surveyId);
        return query.getResultList();
    }
    
}
