package com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class AuditInformationParameterInjection {

	public static MapSqlParameterSource injectParameters(MapSqlParameterSource jdbcMapParameterSource, AuditInformation auditInformation) {
		jdbcMapParameterSource.addValue("createdAt", auditInformation.getCreatedAt());
		jdbcMapParameterSource.addValue("updatedAt", auditInformation.getUpdatedAt());
		return jdbcMapParameterSource;
	}
}
