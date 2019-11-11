package com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders;

import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@NoArgsConstructor
public class JdbcMapParameterSource extends MapSqlParameterSource {
	public JdbcMapParameterSource(String paramName, Object value) {
		super(paramName, value);
	}

	public JdbcMapParameterSource(Map<String, ?> values) {
		super(values);
	}

	@Override
	public Object getValue(String paramName) {
		Object value = super.getValue(paramName);
		return SqlExtractor.extract(value);
	}

	@Override
	public Map<String, Object> getValues() {
		Map<String, Object> values = super.getValues().entrySet().stream()
				.collect(toMap(Map.Entry::getKey, SqlExtractor::extract));
		return Collections.unmodifiableMap(values);
	}
}
