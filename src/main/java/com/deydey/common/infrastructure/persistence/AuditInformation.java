package com.deydey.common.infrastructure.persistence;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.time.Instant;

@Builder
@Value
public class AuditInformation {
	private Instant createdAt;
	private Instant updatedAt;

	public static AuditInformation now() {
		Instant now = Instant.now();
		return AuditInformation.builder()
				.createdAt(now)
				.updatedAt(now)
				.build();
	}
}
