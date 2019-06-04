package com.deydey.repository;

import com.deydey.domain.*;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@Repository
public class MembershipJdbiRepository implements MembershipRepository {

	private Jdbi jdbi;

	private final String SAVE_MEMBERSHIP = "INSERT INTO membership" +
			"(user_id, company_id, email, type, is_primary, verification, phone_number, updated_at)" +
			" VALUES(:userId, :companyId, :email, :type, :isPrimary, :verification, :phoneNumber, now())";

	public MembershipJdbiRepository(Jdbi jdbi) {
		this.jdbi = jdbi;
	}

	@Override
	public Membership save(Membership membership) {
		return jdbi.withHandle(handle -> save(handle, membership));
	}

	@Override
	public Membership save(Handle handle, Membership membership) {
		MembershipVerificationType membershipVerificationType = membership.getMembershipVerificationType();
		return handle.createUpdate(SAVE_MEMBERSHIP)
				.bind("userId", membership.getUser().getId())
				.bind("companyId", membership.getCompany().getId())
				.bind("email", membership.getEmail())
				.bind("type", membership.getMembershipType().toString())
				.bind("isPrimary", membership.getIsPrimary())
				.bind("verification", membershipVerificationType)
				.bind("phoneNumber", membership.getPhoneNumber())
				.executeAndReturnGeneratedKeys()
				.map(new MembershipMapper())
				.one();
	}

	public class MembershipMapper implements RowMapper<Membership> {

		@Override
		public Membership map(ResultSet rs, StatementContext ctx) throws SQLException {

			MembershipType type = rs.getString("type") != null
					? MembershipType.valueOf(rs.getString("type"))
					: null;

			MembershipVerificationType verificationType = rs.getString("verification") != null
				? MembershipVerificationType.valueOf(rs.getString("verification"))
				: null;

			return Membership.builder()
					.id(rs.getLong("id"))
					.user(User.builder().id(rs.getLong("id")).build())
					.company(Company.builder().id(rs.getLong("company_id")).build())
					.email(rs.getString("email"))
					.isPrimary(rs.getBoolean("is_primary"))
					.createdAt(rs.getTimestamp("created_at").toInstant())
					.updatedAt(rs.getTimestamp("updated_at").toInstant())
					.membershipType(type)
					.membershipVerificationType(verificationType)
					.build();
		}
	}
}
