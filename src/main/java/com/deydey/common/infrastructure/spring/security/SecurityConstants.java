package com.deydey.common.infrastructure.spring.security;

import com.deydey.common.infrastructure.spring.ApplicationConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SecurityConstants {


	@Value("${api.auth-secret}")
	@Getter
	private String secret;

	@Bean
	ApplicationConfig applicationConfig(){
		return ApplicationConfig.builder().secret(secret).build();
	}

	@Value("${AUTH_SECRET}")
	@Getter
	private String signingKey;


	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 864_000_000;
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final long VERIFICATION_TOKEN_EXPIRATION_TIME_SECONDS = 3600L;
	public static final long RESET_PASSWORD_TOKEN_EXPIRATION_TIME_SECONDS = 3600L;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users/sign-up";
	public static final String REGISTRATION_CONFIRM_URL = "/users/registration/confirm*";
	public static final String RESET_PASSWORD_URL = "/users/reset-password";
	public static final String RESET_PASSWORD_CONFIRM_URL = "/users/reset-password/confirm";
}
