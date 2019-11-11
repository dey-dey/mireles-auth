package com.deydey.iam.domain.identity.tenant;

import com.deydey.iam.domain.EntityId;
import lombok.Value;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Value
public class TenantId implements EntityId {

	private UUID value;

	public TenantId(UUID value) {
		this.value = value;
	}

	static TenantId getNextValue() {
		return new TenantId(randomUUID());
	}
}
