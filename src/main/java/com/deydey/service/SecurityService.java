package com.deydey.service;

import com.deydey.domain.PasswordResetToken;
import com.deydey.domain.User;
import com.deydey.repository.PasswordResetTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;

@Service
@Slf4j
public class SecurityService {

	private final PasswordResetTokenRepository passwordTokenRepository;

	private static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	public SecurityService(PasswordResetTokenRepository passwordTokenRepository, CustomUserDetailsService customUserDetailsService) {
		this.passwordTokenRepository = passwordTokenRepository;
		this.customUserDetailsService = customUserDetailsService;
	}

	public String validatePasswordResetToken(long id, String token) {
		PasswordResetToken passToken =
				passwordTokenRepository.findByToken(token);
		if ((passToken == null) || (passToken.getUser().getId() != id)) {
			return "invalidToken";
		}

		if (passToken.getExpiryDate().toEpochMilli() - tzUTC.getTimeInMillis() > 60) {
			return "expired";
		}

		User user = passToken.getUser();
		Authentication auth = new UsernamePasswordAuthenticationToken(
				user, null, Collections.singletonList(
				new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return null;
	}


	public void authWithoutPassword(User user) {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}
}
