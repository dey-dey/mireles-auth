package com.deydey.repository;


import com.deydey.domain.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserJdbiRepository implements UserRepository {

	private final Jdbi jdbi;

	private final String SAVE_USER = "INSERT INTO users" +
			"(username, first_name, last_name, updated_at)" +
			" VALUES(:userName, :firstName, :lastName, now());";

	public UserJdbiRepository(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	@Override
	public User save(User user) {
		return jdbi.withHandle(handle -> save(handle, user));
	}

	@Override
	public User save(Handle handle, User user) {
		return handle.createUpdate(SAVE_USER)
				.bind("userName", user.getUsername())
				.bind("firstName", user.getFirstName())
				.bind("lastName", user.getLastName())
				.executeAndReturnGeneratedKeys()
				.map(new UserMapper())
				.one();
	}

	public class UserMapper implements RowMapper<User> {
		@Override
		public User map(ResultSet rs, StatementContext ctx) throws SQLException {
			return User.builder()
					.id(rs.getLong("id"))
					.username(rs.getString("username"))
					.firstName(rs.getString("first_name"))
					.lastName(rs.getString("last_name"))
					.createdAt(rs.getTimestamp("created_at").toInstant())
					.updatedAt(rs.getTimestamp("updated_at").toInstant())
					.build();
		}
	}
}
