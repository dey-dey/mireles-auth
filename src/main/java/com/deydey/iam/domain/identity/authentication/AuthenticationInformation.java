package com.deydey.iam.domain.identity.authentication;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class AuthenticationInformation {
	private String passwordHash;
	private String passwordAlgorithm;
	private String passwordSalt;
}
