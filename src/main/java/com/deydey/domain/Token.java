package com.deydey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class Token {

	private Long id;

	private UUID token;

	private Membership membership;

	private TokenType type;

	private Instant createdAt;

	private Instant updatedAt;

}
