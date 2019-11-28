package com.deydey.iam.application.service;

import com.deydey.common.infrastructure.spring.ApplicationConfig;
import com.deydey.iam.api.dto.RegistrationDto;
import com.deydey.iam.application.command.registration.CreateRegistrationCommand;
import com.deydey.iam.application.translator.RegistrationTranslator;
import com.deydey.iam.domain.access.authorization.RoleService;
import com.deydey.iam.domain.identity.tenant.Tenant;
import com.deydey.iam.domain.identity.tenant.TenantRepository;
import com.deydey.iam.domain.identity.user.Member;
import com.deydey.iam.domain.identity.user.MemberRepository;
import com.deydey.iam.domain.identity.user.User;
import com.deydey.iam.domain.identity.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

import static java.util.Set.*;

@Slf4j
public class RegistrationService {
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private ApplicationConfig applicationConfig;
	private UserRepository userRepository;
	private TenantRepository tenantRepository;
	private RoleService roleService;
	private MemberRepository memberRepository;

	public RegistrationService(BCryptPasswordEncoder bCryptPasswordEncoder, ApplicationConfig applicationConfig, UserRepository userRepository, TenantRepository tenantRepository, RoleService roleService, MemberRepository memberRepository) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.applicationConfig = applicationConfig;
		this.userRepository = userRepository;
		this.tenantRepository = tenantRepository;
		this.roleService = roleService;
		this.memberRepository = memberRepository;
	}

	@Transactional
	// NOTE: breaking aggregate transactional boundaries due to tenant/user mappings
	public RegistrationDto registerUserAsTenant(CreateRegistrationCommand createRegistrationCommand) {
		Tenant tenant = Tenant.newPersonalTenant(createRegistrationCommand);
		User user = User.of(tenant.getTenantId(), createRegistrationCommand);
		Member member = Member.of(user.getId(),
				tenant.getTenantId(),
				createRegistrationCommand,
				bCryptPasswordEncoder,
				applicationConfig);

		tenant.activate();
		tenant.registerMemberWithRole(member.getId(), of());
		user.setPrimaryMember(member);

		tenantRepository.save(tenant);
		userRepository.save(user);
		memberRepository.save(member);

		return RegistrationTranslator.of(user, member, tenant);
	}
}
