package com.deydey.iam.domain.access.authentication;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.common.infrastructure.spring.ApplicationConfig;
import com.deydey.common.infrastructure.spring.security.SecurityConstants;
import com.deydey.iam.domain.identity.user.Member;
import com.deydey.iam.domain.identity.user.MemberId;
import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {
	@Getter
	private AuthenticationId id;
	@Getter
	private MemberId memberId;
	@Getter
	private AuthenticationInformation authenticationInformation;
	@Getter
	private AuditInformation auditInformation;

	public Authentication(MemberId memberId, String password, String passwordSalt, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.id = AuthenticationId.getNext();
		this.memberId = memberId;
		this.authenticationInformation = AuthenticationInformation
				.builder()
				.passwordAlgorithm(bCryptPasswordEncoder.getClass().getName())
				.passwordHash(bCryptPasswordEncoder.encode(password))
				.passwordSalt(passwordSalt)
				.build();
		this.auditInformation = AuditInformation.now();
	}


	public String getPassword() {
		return authenticationInformation.getPasswordHash();
	}
	public String getPasswordAlgorithm() {
		return authenticationInformation.getPasswordAlgorithm();
	}
	public String getPasswordSalt() {
		return authenticationInformation.getPasswordSalt();
	}

 }