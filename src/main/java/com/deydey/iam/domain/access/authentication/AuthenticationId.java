package com.deydey.iam.domain.access.authentication;

import com.deydey.iam.domain.EntityId;
import lombok.Value;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Value
public class AuthenticationId implements EntityId {

	public AuthenticationId(UUID value) {
		this.value = value;
	}

	private UUID value;

	public static AuthenticationId getNext() {
		return new AuthenticationId(randomUUID());
	}
}
