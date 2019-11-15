package com.deydey.iam.infrastructure.persistence.jdbc.mappers;

import com.deydey.iam.domain.identity.tenant.Tenant;
import com.deydey.iam.domain.identity.tenant.TenantId;
import com.deydey.iam.domain.identity.tenant.TenantType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TenantMapper implements SqlMapper<Tenant> {
	@Override
	public Tenant toEntity(ResultSet rs) throws SQLException {
		return Tenant.builder()
				.tenantId(new TenantId(UUID.fromString(rs.getString("id"))))
				.enabled(rs.getBoolean("enabled"))
				.name(rs.getString("name"))
				.tenantType(TenantType.valueOf(rs.getString("type")))
				.build();
	}
}
