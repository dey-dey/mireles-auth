package com.deydey.iam.domain.identity.user;

import com.deydey.iam.domain.EntityId;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Value
public class MemberId implements EntityId {

	public MemberId(Integer value) {
		this.value = value;
	}

	@NonNull
	private Integer value;

	public static MemberId getNextValue() {
		return new MemberId(-1);
	}
}
