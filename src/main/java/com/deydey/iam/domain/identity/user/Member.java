package com.deydey.iam.domain.identity.user;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.common.infrastructure.spring.ApplicationConfig;
import com.deydey.iam.application.command.registration.CreateRegistrationCommand;
import com.deydey.iam.domain.access.authentication.Authentication;
import com.deydey.iam.domain.access.authorization.RoleId;
import com.deydey.iam.domain.identity.tenant.TenantId;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Builder
public class Member {

	@Getter
	private MemberId id;
	@Getter
	private TenantId tenantId;
	@Getter
	private MemberIndentifiableInformation memberIndentifiableInformation;
	@Getter
	private Boolean isPrimary;
	@Getter
	private UserId userId;
	@Getter
	private AuditInformation auditInformation;
	@Getter
	private Authentication authentication;

	public static Member of(UserId userId,
					 TenantId tenantId,
					 CreateRegistrationCommand createRegistrationCommand,
					 BCryptPasswordEncoder bCryptPasswordEncoder,
					 ApplicationConfig applicationConfig) {
		MemberId memberId = MemberId.getNextValue();
		Authentication anAuthentication = new Authentication(memberId,
				createRegistrationCommand.getPassword(),
				applicationConfig.getSecret(),
				bCryptPasswordEncoder);
		return Member.builder()
				.id(memberId)
				.userId(userId)
				.tenantId(tenantId)
				.memberIndentifiableInformation(
						new MemberIndentifiableInformation(
								createRegistrationCommand.getEmail(),
								createRegistrationCommand.getPhoneNumber()))
				.authentication(anAuthentication)
				.auditInformation(AuditInformation.now())
				.build();
	}

	public String getEmail() {
		return memberIndentifiableInformation.getEmail();
	}

	public Boolean isPrimary() {
		return isPrimary;
	}

	public void removePrimaryStatus() {
		isPrimary = Boolean.FALSE;
	}

	public void makePrimaryStatus() {
		isPrimary = Boolean.TRUE;
	}

	public void setId(MemberId id) {
		this.id = id;
	}

	public Authentication getAuthentication() {
		return authentication;
	}
 }