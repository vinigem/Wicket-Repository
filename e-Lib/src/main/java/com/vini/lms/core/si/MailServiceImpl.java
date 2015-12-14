package com.vini.lms.core.si;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
	
	private String FROM="service@e-lib.com";
    @Autowired
    private MailSender mailSender;
    
    
    
	public MailSender getMailSender() {
		return mailSender;
	}



	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}



	@Override
	public void sendRegisterMail(String to) {
		System.out.println("MailServiceImpl.sendRegisterMail()"+mailSender);
		SimpleMailMessage mail = new SimpleMailMessage();
		String message="Thank you for Registering on e-Library\n"+
				"You are now a Registerd Member of e-Library.\n"+
		            "Please Login to 'http://localhost:8080/LMS/login' to use the e-Library services.\n\n"+
				"Regards\n Vinit Kumar";
		
		mail.setFrom(FROM);
		mail.setTo(to);
		mail.setSubject("Registration Successfull!!");
		mail.setText(message);
		mailSender.send(mail);
		

	}

}
