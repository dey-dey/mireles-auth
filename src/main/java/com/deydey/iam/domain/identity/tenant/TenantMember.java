package com.deydey.iam.domain.identity.tenant;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.iam.domain.access.authorization.RoleId;
import com.deydey.iam.domain.identity.user.MemberId;
import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
class TenantMember {

	private TenantMember id;

	@Getter
	private MemberId memberId;

	private TenantId tenantId;

	private AuditInformation auditInformation;

	private Instant startingOn;

	@Getter
	private Set<TenantMemberRole> roles;

	@Nullable
	private Instant retiredOn;

	static public TenantMember of(TenantId tenantId, MemberId memberId, Set<RoleId> roles) {
		return TenantMember.builder()
				.memberId(memberId)
				.auditInformation(AuditInformation.now())
				.startingOn(Instant.now())
				.tenantId(tenantId)
				.roles(toMemberRoles(roles))
				.build();
	}

	public void retire() {
		retiredOn = Instant.now();
	}

	private static Set<TenantMemberRole> toMemberRoles(Set<RoleId> roles) {
		return roles.stream()
				.map(TenantMemberRole::of)
				.collect(Collectors.toSet());
	}
}
