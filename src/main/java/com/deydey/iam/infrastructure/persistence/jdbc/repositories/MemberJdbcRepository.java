package com.deydey.iam.infrastructure.persistence.jdbc.repositories;

import com.deydey.iam.domain.access.authentication.Authentication;
import com.deydey.iam.domain.identity.user.Member;
import com.deydey.iam.domain.identity.user.MemberId;
import com.deydey.iam.domain.identity.user.MemberRepository;
import com.deydey.iam.domain.identity.user.UserIdentityInformation;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.AuditInformationParameterInjection;
import com.deydey.iam.infrastructure.persistence.jdbc.queryBuilders.JdbcMapParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Optional;

public class MemberJdbcRepository implements MemberRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public MemberJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	private String SAVE_MEMBER = "insert into member" +
			"(user_id, tenant_id, email, enabled, phone_number, is_primary, created_at, updated_at) values" +
			"(:userId, :tenantId, :email, :enabled, :phoneNumber, :isPrimary, :createdAt, :updatedAt) " +
			"returning id";
	private String SAVE_AUTHENTICATION = "insert into authentication " +
			"(member_id, active, password_hash, password_algorithm, password_salt, created_at, updated_at) values" +
			"(:memberId, :active, :passwordHash, :passwordAlgorithm, :passwordSalt, :createdAt, :updatedAt)";

	private SqlParameterSource buildInsertMemberParameters(Member member) {
		MapSqlParameterSource parameters = new JdbcMapParameterSource();
		AuditInformationParameterInjection.injectParameters(parameters, member.getAuditInformation());
		parameters.addValue("userId", member.getUserId().getValue());
		parameters.addValue("tenantId", member.getTenantId().getValue());
		parameters.addValue("email", member.getEmail());
		parameters.addValue("enabled", true);
		parameters.addValue("isPrimary", member.isPrimary());
		parameters.addValue("phoneNumber", member.getMemberIndentifiableInformation().getPhoneNumber());
		return parameters;
	}

	@Override
	public Optional<Member> save(Member member) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource parameterSource = buildInsertMemberParameters(member);
		jdbcTemplate.update(SAVE_MEMBER, parameterSource, holder);
		member.setId(
				new MemberId(holder.getKey().intValue()));
		SqlParameterSource authenticationParameters = buildInsertMemberAuthenticationParameters(member);
		jdbcTemplate.update(SAVE_AUTHENTICATION, authenticationParameters);
		return Optional.of(member);
	}

	private SqlParameterSource buildInsertMemberAuthenticationParameters(Member member) {
		MapSqlParameterSource parameters = new JdbcMapParameterSource();
		AuditInformationParameterInjection.injectParameters(parameters, member.getAuditInformation());
		Authentication authentication = member.getAuthentication();
		parameters.addValue("memberId", member.getId());
		parameters.addValue("active", true);
		parameters.addValue("passwordHash", authentication.getPassword());
		parameters.addValue("passwordAlgorithm", authentication.getPasswordAlgorithm());
		parameters.addValue("passwordSalt", authentication.getPasswordSalt());
		return parameters;
	}
}
