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
public class MembershipRole {

	private Long id;

	private Membership membership;

	private Role role;

	private Instant createdAt;

	private Instant updatedAt;
}
