package com.deydey.iam.domain.identity.authentication;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetAddress;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationAttempt {
	private Long id;
	private InetAddress ipAddress;
	private Authentication login;
	private String userAgent;
	private String successful;
	private AuditInformation auditInformation;
}
