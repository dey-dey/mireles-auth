package com.deydey.iam.infrastructure.persistence.jdbc.repositories;

import com.deydey.iam.domain.access.authorization.Role;
import com.deydey.iam.domain.access.authorization.RoleId;
import com.deydey.iam.domain.access.authorization.RoleName;
import com.deydey.iam.domain.access.authorization.RoleRepository;
import com.deydey.iam.infrastructure.persistence.jdbc.mappers.RoleMapper;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.AuditInformationParameterInjection;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.JdbcMapParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

public class RoleJdbcRepository implements RoleRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private String INSERT_ROLE = "insert into role (role, role_description, created_at, updated_at) " +
			"values(:roleName, :roleDescription, :createdAt, :updatedAt)";
	private String GET_ROLE_BY_NAME = "select * from role where role = :roleName";

	public RoleJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Role> getRoles() {
		return null;
	}

	@Override
	public Role getRoleByName(RoleName roleName) {
		MapSqlParameterSource parameters = new JdbcMapParameterSource();
		parameters.addValue("roleName", roleName.toString());
		List<Role> roles = jdbcTemplate.query(GET_ROLE_BY_NAME, parameters, new RoleMapper());
		return roles.stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("role not found"));
	}

	@Override
	public Role save(Role aRole) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(INSERT_ROLE, buildInsertParams(aRole), holder, new String[] { "id" });
		aRole.setId(
				new RoleId(
						Objects.requireNonNull(holder.getKey()).longValue()));
		return aRole;
	}

	private MapSqlParameterSource buildInsertParams(Role aRole) {
		MapSqlParameterSource parameters = new JdbcMapParameterSource();
		AuditInformationParameterInjection.injectParameters(parameters, aRole.getAuditInformation());
		parameters.addValue("roleDescription", aRole.getDescription());
		parameters.addValue("roleName", aRole.getRoleName());
		return parameters;
	}
}
