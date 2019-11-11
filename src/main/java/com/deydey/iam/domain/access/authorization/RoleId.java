package com.deydey.iam.domain.access.authorization;

import lombok.NonNull;
import lombok.Value;

@Value
public class RoleId {
	public RoleId(Long value) {
		this.value = value;
	}

	@NonNull
	private Long value;

}
