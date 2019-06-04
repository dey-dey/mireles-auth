package com.deydey.repository;

import com.deydey.domain.Membership;
import com.deydey.domain.Token;
import com.deydey.domain.TokenType;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TokenJdbiRepository implements TokenRepository {

	private final Jdbi jdbi;

	private final String SAVE_TOKEN = "INSERT into token" +
			"(token, membership_id, type, updated_at)" +
			" VALUES (:token, :membershipId, :type, now());";

	public TokenJdbiRepository(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	@Override
	public Token save(Token token) {
		return jdbi.withHandle(handle -> save(handle, token));
	}

	@Override
	public Token save(Handle handle, Token token) {
		return handle.createUpdate(SAVE_TOKEN)
				.bind("token", token.getToken())
				.bind("membershipId", token.getMembership().getId())
				.bind("type", token.getType())
				.executeAndReturnGeneratedKeys()
				.map(new TokenMapper())
				.one();
	}

	public class TokenMapper implements RowMapper<Token> {

		@Override
		public Token map(ResultSet rs, StatementContext ctx) throws SQLException {
			return Token.builder()
					.id(rs.getLong("id"))
					.token(UUID.fromString(rs.getString("token")))
					.membership(Membership.builder().id(rs.getLong("membership_id")).build())
					.type(TokenType.valueOf(rs.getString("type")))
					.createdAt(rs.getTimestamp("created_at").toInstant())
					.updatedAt(rs.getTimestamp("updated_at").toInstant())
					.build();
		}
	}
}
