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
public class LoginAttempt {

	private Long id;

	private Login login;

	private String userAgent;

	private String successful;

	private Instant createdAt;

}
