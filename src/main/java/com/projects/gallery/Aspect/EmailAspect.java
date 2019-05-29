package com.projects.gallery.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
@Aspect
public class EmailAspect {

	
	//diagnostics
	private static final Logger logger = LoggerFactory.getLogger(EmailAspect.class);
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Value("${inbox.email}")
	private String inboxEmail;
	
	
	@After("execution(* com.projects.gallery.Service.*.uploadFile(..))")
	public void reportUpload(JoinPoint theJoinPoint) {
		
		//get the name of the user currently logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		//see if the method runs
//		logger.info("Inside of uploadFile method!");
		
		SimpleMailMessage emailMessage = new SimpleMailMessage();
				
		emailMessage.setTo(inboxEmail);
		emailMessage.setSubject("Message from Gallery Website!");
		emailMessage.setText(username + " just uploaded an image!");
		
		try {
			mailSender.send(emailMessage);
			} catch (MailException ex) {
				ex.printStackTrace();
			}

	}
}
