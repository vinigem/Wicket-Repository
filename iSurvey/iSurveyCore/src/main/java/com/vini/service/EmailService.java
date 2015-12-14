package com.vini.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    @Qualifier(value = "customeMailMessage")
    private SimpleMailMessage simpleMailMessage;
    
    /**
     * Method to send mail
     */
    public void sendMail(){
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo("Vinit.Kumar2@mindtree.com");
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(String.format(simpleMailMessage.getText(), "Vinit Kumar", "Email Content"));
        }catch (MessagingException e) {
            LOGGER.error("Error While sending mail. {}", e);
            throw new MailParseException(e);
        }
        javaMailSender.send(message);
    }
    
    /**
     * Method to send mail with survey link
     * @param senderEmailId
     * @param URID
     * @param startDate
     * @param endDate
     */
    public void sendSurveyMail(String senderEmailId, String URID, Date startDate, Date endDate) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(senderEmailId);
            helper.setSubject(simpleMailMessage.getSubject());
            String syrveyURL = "http://" + InetAddress.getLocalHost().getHostAddress() +":9080/iSurveyWeb/Survey/TakeSurvey/"+URID;
            helper.setText(String.format(simpleMailMessage.getText(), startDate, endDate, syrveyURL));
        }catch (MessagingException e) {
            throw new MailParseException(e);
        } catch (UnknownHostException e) {
            LOGGER.error("Error While sending survey mail. {}", e);
        }
        javaMailSender.send(message);
    }
    
}
