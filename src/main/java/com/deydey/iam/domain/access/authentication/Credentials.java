package com.deydey.iam.domain.access.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Builder
@Value
public class Credentials {
	private String email;
	private String password;
}