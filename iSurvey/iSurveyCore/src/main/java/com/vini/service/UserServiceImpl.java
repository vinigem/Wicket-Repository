package com.vini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vini.converter.UserConverter;
import com.vini.dao.IUserDao;
import com.vini.util.EmailUtil;
import com.vini.vo.UserVO;

@Service
public class UserServiceImpl implements IUserService {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IUserDao userDao;
    
    private transient UserConverter converter = new UserConverter();
    
    @Transactional
    @Override
    public UserVO getUserByEmailId(String emailId) {
        return converter.convertSource(userDao.getUserByEmailId(EmailUtil.encryptEmailId(emailId)));
    }
    
    @Transactional
    @Override
    public UserVO getUserByRegistrationId(long registrationId) {
        return converter.convertSource(userDao.getUserByRegistrationId(registrationId));
    }
    
    @Transactional
    @Override
    public UserVO registerUserForSurvey(UserVO userVO) {
        long registrationId = generateRegistrationId();
        userVO.setRegistrationId(registrationId);
        return converter.convertSource(userDao.create(converter.convertTarget(userVO)));
    }
    
    private long generateRegistrationId() {
        return userDao.generateRegistrationId();
    }
    
}
