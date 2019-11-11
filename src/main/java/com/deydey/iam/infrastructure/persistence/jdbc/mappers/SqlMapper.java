package com.deydey.iam.infrastructure.persistence.jdbc.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlMapper<T> extends RowMapper<T> {
	T toEntity(ResultSet rs) throws SQLException;

	default T mapRow(ResultSet rs, int rowNum) throws SQLException {
		return toEntity(rs);
	}
}