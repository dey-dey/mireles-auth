package com.deydey.iam.domain.identity.user;

import com.deydey.iam.domain.identity.tenant.TenantId;

import javax.persistence.EntityNotFoundException;

public interface MemberRepository {
	Member save(Member member);
	Member getBy(UserId userId, TenantId tenantId) throws EntityNotFoundException;
}
