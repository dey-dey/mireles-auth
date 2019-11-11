package com.deydey.common.infrastructure.spring.security;

import com.deydey.common.infrastructure.spring.ApplicationConfig;
import com.deydey.iam.application.service.ApplicationUserDetailsService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	private ApplicationConfig applicationConfig;
	private ApplicationUserDetailsService customUserDetailsService;
	public JWTAuthorizationFilter(AuthenticationManager authManager, ApplicationConfig applicationConfig, ApplicationUserDetailsService customUserDetailsService) {
		super(authManager);
		this.applicationConfig = applicationConfig;
		this.customUserDetailsService = customUserDetailsService;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest req,
									HttpServletResponse res,
									FilterChain chain) throws IOException, ServletException {
		String header = req.getHeader(SecurityConstants.HEADER_STRING);
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if (token != null) {
			// parse the token.
			String userName = Jwts.parser()
					.setSigningKey(applicationConfig.getSecret())
					.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			if (userName != null) {
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
				return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			}
			return null;
		}
		return null;
	}
}