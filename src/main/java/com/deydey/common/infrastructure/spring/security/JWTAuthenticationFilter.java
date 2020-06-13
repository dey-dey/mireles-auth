package com.deydey.common.infrastructure.spring.security;

import com.deydey.common.infrastructure.spring.ApplicationConfig;
import com.deydey.iam.application.service.SecurityService;
import com.deydey.iam.domain.access.authorization.Role;
import com.deydey.iam.domain.identity.authentication.Credentials;
import com.deydey.iam.domain.identity.authentication.CredentialsService;
import com.deydey.iam.domain.identity.user.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.deydey.iam.domain.identity.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.deydey.common.infrastructure.spring.security.SecurityConstants.EXPIRATION_TIME;
import static com.deydey.common.infrastructure.spring.security.SecurityConstants.HEADER_STRING;
import static com.deydey.common.infrastructure.spring.security.SecurityConstants.TOKEN_PREFIX;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	private ApplicationConfig applicationConfig;
	private CredentialsService credentialsService;
	private SecurityService securityService;

	JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationConfig applicationConfig, CredentialsService credentialsService, SecurityService securityService) {
		this.authenticationManager = authenticationManager;
		this.applicationConfig = applicationConfig;
		this.credentialsService = credentialsService;
		this.securityService = securityService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
												HttpServletResponse res) throws AuthenticationException {
		try {
			User user = new ObjectMapper()
					.readValue(req.getInputStream(), User.class);
			Member userMember = user.getPrimary();
			Credentials credentials = credentialsService.credentialsOfMember(userMember);
			List<Role> roles = securityService.findRolesForMember(userMember);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							credentials.getEmail(),
							credentials.getPassword(),
							roles)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
		String token = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, applicationConfig.getSecret())
				.compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}