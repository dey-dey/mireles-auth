package com.deydey.iam.infrastructure.persistence.jdbc.repositories;

import com.deydey.iam.domain.identity.tenant.Tenant;
import com.deydey.iam.domain.identity.tenant.TenantRepository;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.AuditInformationParameterInjection;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.JdbcMapParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class TenantJdbcRepository implements TenantRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public TenantJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private String SAVE_TENANT = "insert into tenant" +
			"(id, enabled, name, type, created_at, updated_at) values" +
			"(:id, :enabled, :name, :type, :createdAt, :updatedAt)";

	@Override
	public Tenant save(Tenant tenant) {
		SqlParameterSource parameters = buildInsertUserParameters(tenant);
		jdbcTemplate.update(SAVE_TENANT, parameters);
		return tenant;
	}

	private SqlParameterSource buildInsertUserParameters(Tenant tenant) {
		MapSqlParameterSource parameters = new JdbcMapParameterSource();
		parameters.addValue("id", tenant.getTenantId());
		parameters.addValue("name", tenant.getName());
		parameters.addValue("enabled", tenant.getEnabled());
		parameters.addValue("type", tenant.getTenantType().toString());
		AuditInformationParameterInjection.injectParameters(parameters, tenant.getAuditInformation());
		return parameters;
	}

	@Override
	public Boolean memberOfPersonalTenant(String memberEmail) {
		return null;
	}
}
