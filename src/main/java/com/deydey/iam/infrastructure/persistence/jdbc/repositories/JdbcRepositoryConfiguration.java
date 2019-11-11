package com.deydey.iam.infrastructure.persistence.jdbc.repositories;

import com.deydey.iam.domain.access.authorization.RoleRepository;
import com.deydey.iam.domain.identity.tenant.TenantRepository;
import com.deydey.iam.domain.identity.user.MemberRepository;
import com.deydey.iam.domain.identity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class JdbcRepositoryConfiguration {

	@Autowired
	private DataSource dataSource;

	@Bean
	public UserRepository userRepository() {
		return new UserJdbcRepository(namedParameterJdbcTemplate());
	}

	@Bean
	public MemberRepository memberRepository() {
		return new MemberJdbcRepository(namedParameterJdbcTemplate());
	}

	@Bean
	public TenantRepository tenantRepository() {
		return new TenantJdbcRepository(namedParameterJdbcTemplate());
	}

	@Bean
	public RoleRepository roleRepository() {
		return new RoleJdbcRepository(namedParameterJdbcTemplate());
	}

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource);
	}
}
