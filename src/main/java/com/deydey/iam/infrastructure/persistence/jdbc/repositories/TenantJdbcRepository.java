package com.deydey.iam.infrastructure.persistence.jdbc.repositories;

import com.deydey.iam.domain.identity.tenant.Tenant;
import com.deydey.iam.domain.identity.tenant.TenantId;
import com.deydey.iam.domain.identity.tenant.TenantRepository;
import com.deydey.iam.infrastructure.persistence.jdbc.mappers.TenantMapper;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.AuditInformationParameterInjection;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.JdbcMapParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class TenantJdbcRepository implements TenantRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public TenantJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private String SAVE_TENANT = "insert into tenant" +
			"(id, enabled, name, type, created_at, updated_at) values" +
			"(:id, :enabled, :name, :type, :createdAt, :updatedAt)";
	private String GET_BY_ID = "select * from tenant tnt where tnt.id = :tenantId";

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

	@Override
	public Tenant getBy(TenantId tenantId) {
		MapSqlParameterSource parameterSource = new JdbcMapParameterSource();
		parameterSource.addValue("tenantId", tenantId);
		List<Tenant> queryResults = jdbcTemplate.query(GET_BY_ID, parameterSource, new TenantMapper());
		return queryResults.stream().findFirst().orElseThrow(() -> new EntityNotFoundException("tenant not found by id"));
	}
}
