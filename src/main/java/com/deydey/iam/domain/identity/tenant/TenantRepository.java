package com.deydey.iam.domain.identity.tenant;

public interface TenantRepository {
	Tenant save(Tenant tenant);
	Boolean memberOfPersonalTenant(String memberEmail);
}
