package com.deydey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {

	private Long id;

	private Membership membership;

	private String passwordHash;

	private String passwordAlgorithm;

	private String passwordSalt;

	private Instant createdAt;

	private Instant updatedAt;
}