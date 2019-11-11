package com.deydey.iam.domain.identity.tenant;

import com.deydey.common.infrastructure.persistence.AuditInformation;
import com.deydey.iam.domain.identity.user.MemberId;
import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@AllArgsConstructor
@Builder
class TenantMember {

	private UUID id;

	private MemberId memberId;

	private TenantId tenantId;

	private AuditInformation auditInformation;

	private Instant startingOn;

	@Nullable
	private Instant retiredOn;

	static TenantMember of(TenantId tenantId, MemberId memberId) {
		return TenantMember.builder()
				.memberId(memberId)
				.auditInformation(AuditInformation.now())
				.startingOn(Instant.now())
				.tenantId(tenantId)
				.build();
	}
}
