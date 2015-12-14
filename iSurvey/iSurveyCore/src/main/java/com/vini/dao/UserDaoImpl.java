package com.vini.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.vini.ec.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements IUserDao{
    private static final long serialVersionUID = 1L;
    
    @Override
    public User getUserByEmailId(String emailId) {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE emailId = :emailId", User.class);
        query.setParameter("emailId", emailId);
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }
    
    @Override
    public User getUserByRegistrationId(long registrationId) {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE registrationId = :registrationId", User.class);
        query.setParameter("registrationId", registrationId);
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }
    
    @Override
    public long generateRegistrationId() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(registrationId) FROM User", Long.class);
        Long registrationId = query.getSingleResult();
        return registrationId == null ? 1000000000 : registrationId + 1;
    }
    
    
}
