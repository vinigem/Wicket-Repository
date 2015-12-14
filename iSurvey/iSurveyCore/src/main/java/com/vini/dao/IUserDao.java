package com.vini.dao;

import com.vini.ec.User;

public interface IUserDao extends GenericDao<User> {
    
    User getUserByEmailId(String emailId);
    
    User getUserByRegistrationId(long registrationId);
    
    long generateRegistrationId();
    
    
}
