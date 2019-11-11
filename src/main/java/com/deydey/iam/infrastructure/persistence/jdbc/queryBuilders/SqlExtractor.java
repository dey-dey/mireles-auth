package com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders;

import com.deydey.iam.domain.EntityId;
import com.deydey.iam.infrastructure.persistence.jdbc.Payload;
import org.postgresql.util.PGobject;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class SqlExtractor {

	public static Object extract(Object value) {
		if (value instanceof Instant) {
			return Timestamp.from((Instant) value);
		}

		if (value instanceof Enum<?>) {
			return ((Enum) value).name();
		}

		if (value instanceof EntityId) {
			return ((EntityId) value).getValue();
		}

		if (value instanceof Optional) {
			return ((Optional<?>) value)
					.map(SqlExtractor::extract)
					.orElse(null);
		}

		if (value instanceof Collection) {
			return ((Collection<?>) value).stream()
					.map(SqlExtractor::extract)
					.collect(toList());
		}

		if (value instanceof Payload) {
			return extractPayload((Payload) value);
		}

		return value;
	}

	private static PGobject extractPayload(Payload value) {
		PGobject jsonPayload = new PGobject();
		jsonPayload.setType("jsonb");
		String payloadValue = value.getValue();
		try {
			jsonPayload.setValue(payloadValue);
		} catch (SQLException e) {
			throw new RuntimeException("Invalid json object: " + payloadValue);
		}
		return jsonPayload;
	}
}
