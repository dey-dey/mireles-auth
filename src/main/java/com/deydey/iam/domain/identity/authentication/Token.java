package com.deydey.iam.domain.identity.authentication;

import com.deydey.iam.domain.identity.user.Member;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class Token {

	private Long id;

	private UUID token;

	private Member member;

	private TokenType type;

	private Instant createdAt;

	private Instant updatedAt;

}
