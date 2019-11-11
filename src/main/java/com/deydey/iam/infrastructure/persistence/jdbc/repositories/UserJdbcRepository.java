package com.deydey.iam.infrastructure.persistence.jdbc.repositories;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.iam.domain.identity.tenant.TenantId;
import com.deydey.iam.domain.identity.user.Member;
import com.deydey.iam.domain.identity.user.User;
import com.deydey.iam.domain.identity.user.UserDoesNotExistException;
import com.deydey.iam.domain.identity.user.UserId;
import com.deydey.iam.domain.identity.user.UserIdentityInformation;
import com.deydey.iam.domain.identity.user.UserRepository;
import com.deydey.iam.infrastructure.persistence.jdbc.mappers.MemberMapper;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.AuditInformationParameterInjection;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.JdbcMapParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserJdbcRepository implements UserRepository {

	private final String SAVE_USER = "INSERT INTO users" +
			"(id, tenant_id, first_name, last_name, default_email, created_at, updated_at) VALUES" +
			"(:id, :tenantId, :firstName, :lastName, :defaultEmail, :createdAt, :updatedAt);";
	private final String GET_USER_BY_ID = "select * from users where id = :userId";
	private final String GET_USER_MEMBERS = "select * from member where user_id = :userId";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public UserJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private SqlParameterSource buildInsertUserParameters(User user) {
		UserIdentityInformation userIdentityInformation = user.getUserIdentityInformation();
		MapSqlParameterSource parameters = new JdbcMapParameterSource();
		AuditInformationParameterInjection.injectParameters(parameters, user.getAuditInformation());
		parameters.addValue("id", user.getId());
		parameters.addValue("tenantId", user.getTenantId().getValue());
		parameters.addValue("firstName", userIdentityInformation.getFirstName());
		parameters.addValue("lastName", userIdentityInformation.getLastName());
		parameters.addValue("defaultEmail", userIdentityInformation.getDefaultEmail());
		return parameters;
	}


	@Override
	public User save(User user) {
		SqlParameterSource parameterSource = buildInsertUserParameters(user);
		jdbcTemplate.update(SAVE_USER, parameterSource);
		return user;
	}

	@Override
	public Optional<User> getUserBy(UserId userId) {
		MapSqlParameterSource parameters = new JdbcMapParameterSource();
		parameters.addValue("userId", userId.getValue());
		List<User> returnedUsers = jdbcTemplate.query(GET_USER_BY_ID, parameters, getUserRowMapper());
		List<Member> members = jdbcTemplate.query(GET_USER_MEMBERS, parameters, new MemberMapper());
		if (returnedUsers.size() > 1) {
			throw new IllegalStateException("more than one user by this id");
		}
		if (returnedUsers.size() == 0) {
			throw new UserDoesNotExistException("user does not exist with the provided id");
		}
		User user = returnedUsers.get(0);
		user.setMembers(members);
		return Optional.of(user);
	}

	private RowMapper<User> getUserRowMapper() {
		return (rs, rowNum) -> User.builder()
				.id(new UserId(UUID.fromString(rs.getString("id"))))
				.auditInformation(AuditInformation.builder()
						.createdAt(rs.getTimestamp("created_at").toInstant())
						.updatedAt(rs.getTimestamp("updated_at").toInstant())
						.build())
				.tenantId(new TenantId(UUID.fromString(rs.getString("tenant_id"))))
				.userIdentityInformation(
						new UserIdentityInformation(rs.getString("default_email"),
							rs.getString("first_name"),
							rs.getString("last_name")))
				.build();
	}
}
