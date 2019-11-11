package com.deydey.iam.domain.identity.user;

import com.deydey.iam.domain.EntityId;
import lombok.Value;

import java.util.UUID;

@Value
public
class UserId implements EntityId {
	private UUID value;

	static UUID nextValue() {
		return UUID.randomUUID();
	}
 }
