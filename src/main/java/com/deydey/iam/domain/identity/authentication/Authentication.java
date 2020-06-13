package com.deydey.iam.domain.identity.authentication;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.iam.domain.identity.user.MemberId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;

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
	@Getter
	private Instant activeFrom;
	@Getter
	private Instant activeTo;

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
		this.activeFrom = Instant.now();
		this.activeTo = null;
	}

	public boolean isActive() {
		return activeTo != null;
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