package com.deydey.iam.infrastructure.persistence.jdbc.repositories;

import com.deydey.iam.domain.access.authorization.Role;
import com.deydey.iam.domain.access.authorization.RoleName;
import com.deydey.iam.domain.access.authorization.RoleRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class RoleJdbcRepository implements RoleRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public RoleJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Role> getRoles() {
		return null;
	}

	@Override
	public Role getRoleByName(RoleName roleName) {
		return null;
	}
}
