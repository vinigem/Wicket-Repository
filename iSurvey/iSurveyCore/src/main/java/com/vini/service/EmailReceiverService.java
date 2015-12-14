package com.vini.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vini.util.EmailUtil;
import com.vini.util.SurveyUtil;
import com.vini.vo.SurveyVO;
import com.vini.vo.UserVO;

@Service
public class EmailReceiverService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailReceiverService.class);
    
    @Autowired
    private ISurveyService surveyService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private EmailService emailService;
    
    /**
     * Method to handle email receive event.
     * @param recievedMessage
     */
    public void receive(MimeMessage receivedMessage) {
        try {
            MimeMessageParser messageParser = new MimeMessageParser(receivedMessage).parse();
            String senderEmailId = messageParser.getFrom();
            String subject = messageParser.getSubject();
            LOGGER.info("From: {}, Subject: {} ", senderEmailId, subject);
            
            registerSenderForSurvey(senderEmailId, subject);
        } catch (MessagingException e) {
            LOGGER.error("Error while reading email. {}", e);
        } catch (Exception e) {
            LOGGER.error("Error while parsing email. {}", e);
        }
        
    }
    
    /**
     * Method to register the user for the survey and email the survey link.
     * @param senderEmailId
     * @param subject
     */
    private void registerSenderForSurvey(String senderEmailId, String subject) {
        SurveyVO surveyVO = surveyService.getSurveyByName(subject);
        if(surveyVO != null){
            // Check id User already registered or not, if registered email the previous survey link, if not create a new entry and email new survey link.
            UserVO userVO = userService.getUserByEmailId(senderEmailId);
            if(userVO == null){
                userVO = new UserVO();
                userVO.setEmailId(EmailUtil.encryptEmailId(senderEmailId));
                userVO.setSurveyId(surveyVO.getSurveyId());
                userVO = userService.registerUserForSurvey(userVO);
                LOGGER.info("User Successfully registered for the Survey [{}].", subject);
            }
            emailService.sendSurveyMail(senderEmailId, SurveyUtil.encryptURID(userVO.getRegistrationId()), surveyVO.getStartDate(), surveyVO.getEndDate());
            LOGGER.info("Mail with the survey link sent successfully.");
        }else{
            LOGGER.warn("No survey found for name {}", subject);
        }
    }
    
    
}
