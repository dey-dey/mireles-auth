package com.deydey.iam.infrastructure.persistence.jdbc.mappers;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.iam.domain.access.authentication.Authentication;
import com.deydey.iam.domain.access.authentication.AuthenticationId;
import com.deydey.iam.domain.identity.tenant.TenantId;
import com.deydey.iam.domain.identity.user.Member;
import com.deydey.iam.domain.identity.user.MemberId;
import com.deydey.iam.domain.identity.user.MemberIndentifiableInformation;
import com.deydey.iam.domain.identity.user.UserId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MemberMapper implements SqlMapper<Member> {

	@Override
	public Member toEntity(ResultSet rs) throws SQLException {
		return Member.builder()
				.id(new MemberId(rs.getInt("id")))
				.userId(new UserId(UUID.fromString( rs.getString("user_id"))))
				.tenantId(new TenantId(UUID.fromString( rs.getString("tenant_id"))))
				.isPrimary(rs.getBoolean("is_primary"))
				.memberIndentifiableInformation(
						MemberIndentifiableInformation.builder()
								.email(rs.getString("email"))
								.phoneNumber(rs.getString("phone_number"))
							.build())
				.authentication(
						Authentication.builder()
							.build())
				.auditInformation(
						AuditInformation.builder()
							.createdAt(rs.getTimestamp("created_at").toInstant())
							.createdAt(rs.getTimestamp("updated_at").toInstant())
							.build())
				.build();
	}
}
