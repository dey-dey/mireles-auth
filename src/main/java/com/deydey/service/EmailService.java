package com.deydey.service;

import com.deydey.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Component
public class EmailService {

//	@Inject
//	private ApplicationContext context;


	@Value("support.email")
	private String supportEmail;

	private SimpleMailMessage constructResetTokenEmail(
			String contextPath, Locale locale, String token, User user) {
//		String url = contextPath + "/user/changePassword?id=" +
//				user.getId() + "&token=" + token;
//		String message = messages.getMessage("message.resetPassword",
//				null, locale);
		return constructEmail("Reset Password", "hello	", user);
	}

	private SimpleMailMessage constructEmail(String subject, String body,
											 User user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(supportEmail);
		return email;
	}
}
