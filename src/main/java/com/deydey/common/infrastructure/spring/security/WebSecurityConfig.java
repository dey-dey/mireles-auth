package com.deydey.common.infrastructure.spring.security;

import com.deydey.common.infrastructure.spring.ApplicationConfig;
import com.deydey.iam.application.service.ApplicationUserDetailsService;
import com.deydey.iam.application.service.SecurityService;
import com.deydey.iam.domain.identity.authentication.CredentialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@Slf4j
@Profile({"development", "production"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final ApplicationUserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ApplicationConfig applicationConfig;
	private final SecurityService securityService;
	private final CredentialsService credentialsService;

	@Autowired
	public WebSecurityConfig(ApplicationUserDetailsService userDetailsService,
							 ApplicationConfig applicationConfig,
							 SecurityService securityService,
							 CredentialsService credentialsService) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.applicationConfig = applicationConfig;
		this.securityService = securityService;
		this.credentialsService = credentialsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers(SecurityConstants.SIGN_UP_URL,
						SecurityConstants.RESET_PASSWORD_URL,
						SecurityConstants.RESET_PASSWORD_CONFIRM_URL,
						SecurityConstants.REGISTRATION_CONFIRM_URL)
				.permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(authenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), applicationConfig, userDetailsService));
	}

	private JWTAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
		JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager, applicationConfig, credentialsService, securityService);
		jwtAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
		return jwtAuthenticationFilter;
	}
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return userDetailsService;
	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return bCryptPasswordEncoder;
	}
}